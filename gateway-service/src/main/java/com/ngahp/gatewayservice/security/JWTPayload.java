package com.ngahp.gatewayservice.security;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTPayload {
	private List<String> authorities;
	private String role;
	private Boolean authenticated;
	private String tokenKey;
	private Long userId;
	private String userName;
	private String smsOtp;
}
