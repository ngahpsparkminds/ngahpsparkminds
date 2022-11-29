package com.ngahp.userservice.service.event;

import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.enums.TokenType;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class CreateUserEvent {
	private User user;
	private TokenType tokenType;
	private Long expiration;
	private String token;
}
