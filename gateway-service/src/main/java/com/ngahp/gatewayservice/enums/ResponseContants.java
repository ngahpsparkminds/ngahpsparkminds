package com.ngahp.gatewayservice.enums;

import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseContants {

	TOKEN_EXPIRED(HttpServletResponse.SC_UNAUTHORIZED, "The token has expired"),

	TOKEN_INVALID(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token"),

	SESSION_OUT(HttpServletResponse.SC_UNAUTHORIZED, "The session was out"),

	INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong"),

	ACCOUNT_BLOCKED_BY_ADMIN(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
			"Your account has been blocked by the admin"),

	VALID_TOKEN(HttpServletResponse.SC_OK, "OK"),
	
	LACK_VERIFY_2FA(HttpServletResponse.SC_NOT_ACCEPTABLE, "Lack of verify Two-factor authentication"),

	MAINTENANCE_MODE_ON(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Maintenance mode on"),

	SUCCESS(HttpServletResponse.SC_OK, "Success");

	private int code;
	private String message;
}
