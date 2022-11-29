package com.ngahp.authenticationservice.service.impl;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ngahp.authenticationservice.constant.ErrorCodeConstant;
import com.ngahp.authenticationservice.dto.request.LoginInformationDto;
import com.ngahp.authenticationservice.dto.response.AuthenticatedInformationDto;
import com.ngahp.authenticationservice.exception.UnauthorizeException;
import com.ngahp.authenticationservice.model.User;
import com.ngahp.authenticationservice.security.JwtTokenProvider;
import com.ngahp.authenticationservice.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;

	@Override
	public AuthenticatedInformationDto authenticate(LoginInformationDto loginDto) {
		try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            		loginDto.getEmail(), loginDto.getPassword());
			return successfulAuthentication(authenticationManager.authenticate(authToken));
		} catch (AuthenticationException e) {
			log.error("Authentication Fail", e);
			return unSuccessfulAuthentication(e);
		}
	}

	public AuthenticatedInformationDto successfulAuthentication(Authentication auth) {
		String sessionId = UUID.randomUUID().toString();
		User user = (User) auth.getPrincipal();
		String token = tokenProvider.generateToken(sessionId, user);
		String refreshToken = tokenProvider.generateRefreshToken(token, user);
		return AuthenticatedInformationDto.builder()
				.token(refreshToken)
				.refreshToken(refreshToken)
				.build();
	}

	public AuthenticatedInformationDto unSuccessfulAuthentication(AuthenticationException ex) {
		if(ex instanceof LockedException) {
			throw new UnauthorizeException(ErrorCodeConstant.ACCOUNT_BLOCKED);
		}
		throw new UnauthorizeException(ErrorCodeConstant.INVALID_CREDENTIALS);
	}
}
