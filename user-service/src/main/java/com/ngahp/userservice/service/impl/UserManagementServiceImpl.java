package com.ngahp.userservice.service.impl;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngahp.userservice.constant.ErrorMessageConstant;
import com.ngahp.userservice.entity.EmailToken;
import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.enums.TokenType;
import com.ngahp.userservice.exception.BadRequestException;
import com.ngahp.userservice.exception.TokenExpiredException;
import com.ngahp.userservice.repository.EmailTokenRepository;
import com.ngahp.userservice.repository.UserRepository;
import com.ngahp.userservice.service.EmailTokenService;
import com.ngahp.userservice.service.UserManagementService;
import com.ngahp.userservice.service.dto.request.ResetPasswordRequestDto;
import com.ngahp.userservice.service.dto.request.UserRequestDto;
import com.ngahp.userservice.service.dto.response.UserAuthenticationResponseDto;
import com.ngahp.userservice.service.event.CreateUserEvent;
import com.ngahp.userservice.service.mapper.UserMapper;
import com.ngahp.userservice.util.RandomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserManagementServiceImpl implements UserManagementService {
	private static final long CREATE_USER_EXPIRATION_IN_MILLIS = 86400000L;
	/**Repositories**/
	private final UserRepository userRepository;
	private final EmailTokenRepository emailTokenRepository;

	/**Mappers**/
	private final UserMapper userMapper;

	/**Services**/
	private final EmailTokenService emailTokenService;

	private final ApplicationEventPublisher applicationEvent;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void addUser(UserRequestDto dto) {
		Optional<User>user = userRepository.findByEmail(dto.getEmail());
		if(user.isPresent() && !user.get().isDeleted()) {
			log.warn("User already existed");
			throw new BadRequestException("user-error.user-already-existed");
		}
		User persistedUser = Optional.ofNullable(userMapper.toEntity(dto))
				.map(userRepository::save)
				.orElseThrow(()-> new BadRequestException("user-error.persists-user-fail"));
		
		String uuidToken = RandomUtil.generateUUIDString();

		applicationEvent.publishEvent(CreateUserEvent.builder()
				.user(persistedUser)
				.expiration(CREATE_USER_EXPIRATION_IN_MILLIS)
				.tokenType(TokenType.RESET_PASSWORD)
				.token(uuidToken).build());
		//Send mail URL for set user password
	}

	@Transactional(noRollbackFor = {TokenExpiredException.class})
	@Override
	public void resetPassword(ResetPasswordRequestDto dto) {
		EmailToken token = emailTokenService.verifyEmailToken(dto.getToken(), TokenType.RESET_PASSWORD);
		User user = token.getUser();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		emailTokenRepository.delete(token);
	}

	@Override
	public UserAuthenticationResponseDto getUserAuthInformation(String email) {
		return userRepository.findByEmail(email)
					.map(userMapper::toAuthDto)
					.orElseThrow(()-> new BadRequestException(ErrorMessageConstant.USER_NOT_FOUND));
	}
}
