package com.ngahp.userservice.service.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.ngahp.userservice.entity.EmailToken;
import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.enums.TokenType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmailTokenMapper {
	@Mapping(target = "token", source = "token")
	@Mapping(target = "expiration", source = "expiration")
	EmailToken fromExsited(@MappingTarget EmailToken existed, String token, Instant expiration);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", source = "user")
	EmailToken toEmailToken(User user, TokenType tokenType, String token, Instant expiration);
}
