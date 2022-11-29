package com.ngahp.authenticationservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ngahp.authenticationservice.dto.response.UserAuthenticationResponseDto;
import com.ngahp.authenticationservice.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDetailMapper {
	@Mapping(target = "authorities", expression = "java(java.util.List.of())")
	@Mapping(target = "blocked", expression = "java(dto.getUserStatus() == com.ngahp.authenticationservice.enums.UserStatus.BLOCK)")
	User toUserDetail(UserAuthenticationResponseDto dto);
}
