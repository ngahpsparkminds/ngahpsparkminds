package com.ngahp.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ngahp.userservice.entity.EmailToken;
import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.enums.TokenType;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
	Optional<EmailToken> findByUserAndTokenType(User user, TokenType tokenType);

	Optional<EmailToken> findByTokenAndTokenType(String token, TokenType tokenType);
}
