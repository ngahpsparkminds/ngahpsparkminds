package com.ngahp.userservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageConstant {
	public static final String REQUIRE_VALIDATE = "user.validation.required";
	public static final String SIZE_VALIDATE = "user.validation.length-invalid";
	public static final String EMAIL_TOKEN_NOT_FOUND = "user.email-token.not-found";
	public static final String EMAIL_TOKEN_EXPIRED = "user.email-token.expired";
	public static final String USER_NOT_FOUND = "user.user-not-found-by-email";
}
