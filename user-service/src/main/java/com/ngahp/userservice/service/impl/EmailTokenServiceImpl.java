package com.ngahp.userservice.service.impl;

import static com.ngahp.userservice.constant.ErrorMessageConstant.EMAIL_TOKEN_EXPIRED;
import static com.ngahp.userservice.constant.ErrorMessageConstant.EMAIL_TOKEN_NOT_FOUND;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngahp.userservice.entity.EmailToken;
import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.enums.TokenType;
import com.ngahp.userservice.exception.BadRequestException;
import com.ngahp.userservice.exception.TokenExpiredException;
import com.ngahp.userservice.repository.EmailTokenRepository;
import com.ngahp.userservice.service.EmailTokenService;
import com.ngahp.userservice.service.mapper.EmailTokenMapper;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class EmailTokenServiceImpl implements EmailTokenService{
	private final EmailTokenRepository emailTokenRepository;
	private final EmailTokenMapper emailTokenMapper;

	@Override
	public void generateEmailToken(User user, String uuid, TokenType tokenType, Long expiration) {
		Instant expiry = Instant.now().plus(expiration, ChronoUnit.MILLIS);
		emailTokenRepository.findByUserAndTokenType(user, tokenType)
							.map(token-> emailTokenMapper.fromExsited(token, uuid, expiry))
							.or(()-> Optional.ofNullable(emailTokenMapper.toEmailToken(user, tokenType, uuid, expiry)))
							.ifPresent(emailTokenRepository::save);
	}

	@Transactional(noRollbackFor = {TokenExpiredException.class})
	@Override
	public EmailToken verifyEmailToken(String token, TokenType tokenType) {
		return emailTokenRepository.findByTokenAndTokenType(token, tokenType)
							.map(t-> Optional.of(t).filter(Predicate.not(this::isExpired))
									.orElseThrow(()->{
										emailTokenRepository.delete(t);
										return new TokenExpiredException(EMAIL_TOKEN_EXPIRED);
									})
									).orElseThrow(()-> new BadRequestException(EMAIL_TOKEN_NOT_FOUND));
	}

	private boolean isExpired(EmailToken token) {
		return Instant.now().isAfter(token.getExpiration());
	}
}
