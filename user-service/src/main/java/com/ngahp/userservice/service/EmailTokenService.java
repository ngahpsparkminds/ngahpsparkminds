package com.ngahp.userservice.service;

import com.ngahp.userservice.entity.EmailToken;
import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.enums.TokenType;

public interface EmailTokenService {
	void generateEmailToken(User user, String token, TokenType tokenType, Long expiration);
	EmailToken verifyEmailToken(String token, TokenType tokenType);
}
