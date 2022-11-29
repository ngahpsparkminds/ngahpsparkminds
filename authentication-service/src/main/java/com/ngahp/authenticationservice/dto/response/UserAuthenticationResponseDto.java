package com.ngahp.authenticationservice.dto.response;

import com.ngahp.authenticationservice.enums.UserStatus;

import lombok.Data;

@Data
public class UserAuthenticationResponseDto {
	private Long userId;
	private String email;
	private String password;
	private UserStatus userStatus;
}
