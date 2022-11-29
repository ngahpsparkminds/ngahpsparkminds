package com.ngahp.userservice.service.dto.response;

import com.ngahp.userservice.enums.UserStatus;

import lombok.Data;

@Data
public class UserAuthenticationResponseDto {
	private Long id;
	private String email;
	private String password;
	private UserStatus userStatus;
}
