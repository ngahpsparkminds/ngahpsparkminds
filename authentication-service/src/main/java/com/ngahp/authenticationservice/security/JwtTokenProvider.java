package com.ngahp.authenticationservice.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.ngahp.authenticationservice.config.ApplicationProperties;
import com.ngahp.authenticationservice.config.ApplicationProperties.ApplicationSecurity;
import com.ngahp.authenticationservice.config.ApplicationProperties.Jwt;
import com.ngahp.authenticationservice.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private final ApplicationProperties applicationProperties;
	private Jwt jwt;
	private Key key;
	
	@PostConstruct
	public void init() {
		jwt = Optional.ofNullable(applicationProperties.getSecurity())
				.map(ApplicationSecurity::getJwt)
				.orElseThrow(()-> new RuntimeException("application properties is null!"));
		byte[] keyBytes = Base64.getDecoder().decode(jwt.getKey());
		key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(String random, User user) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setSubject(user.getUsername())
				.claim("authorities",
						user.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.claim("tokenKey", random)
				.claim("userId", user.getUserId())
				.claim("username", user.getUsername())
				.claim("isRefresh", false)
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + jwt.getTokenExpiration()))
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}
	public String generateRefreshToken(String random, User user) {
		long now = System.currentTimeMillis();
		return Jwts.builder().setSubject(user.getUsername())
				.claim("authorities",
						user.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.claim("tokenKey", random)
				.claim("userId", user.getUserId())
				.claim("username", user.getUsername())
				.claim("isRefresh", true)
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + jwt.getRenewExpiration()))
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}
}
