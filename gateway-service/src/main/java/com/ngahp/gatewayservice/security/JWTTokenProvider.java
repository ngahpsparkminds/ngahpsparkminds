package com.ngahp.gatewayservice.security;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ngahp.gatewayservice.config.JwtConfig;
import com.ngahp.gatewayservice.enums.ResponseContants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTTokenProvider {
	private final JwtConfig jwtConfig;

	// JWT claims properties
	private static final String AUTHORITIES = "authorities";
	private static final String TOKEN_KEY = "tokenKey";
	private static final String USER_ID = "userId";
	private static final String USER_NAME = "username";
	private static final String IS_REFRESH = "isRefresh";

	public JWTPayload extractJwtToken(String jwtToken) {
		Claims claims = Jwts.parserBuilder().setSigningKey(jwtConfig.getSecret().getBytes()).build().parseClaimsJws(jwtToken).getBody();
		JWTPayload payload = new JWTPayload();
		payload.setAuthorities(claims.get(AUTHORITIES, List.class));
		payload.setTokenKey(claims.get(TOKEN_KEY, String.class));
		payload.setUserId(claims.get(USER_ID, Long.class));
		payload.setUserName(claims.get(USER_NAME, String.class));
		return payload;
	}

	public ResponseContants validateJwtToken(String jwtToken) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(jwtConfig.getSecret().getBytes()).build().parseClaimsJws(jwtToken).getBody();
			if (Boolean.TRUE.equals(claims.get(IS_REFRESH, Boolean.class))) {
				return ResponseContants.TOKEN_INVALID;
			}
			return ResponseContants.VALID_TOKEN;
		} catch (SignatureException e) {
			return ResponseContants.TOKEN_INVALID;
		} catch (ExpiredJwtException e) {
			return ResponseContants.TOKEN_EXPIRED;
		} catch (Exception e) {
			return ResponseContants.INTERNAL_SERVER_ERROR;
		}
	}
}
