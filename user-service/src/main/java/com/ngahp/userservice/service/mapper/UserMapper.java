package com.ngahp.userservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ngahp.userservice.entity.User;
import com.ngahp.userservice.service.dto.request.UserRequestDto;
import com.ngahp.userservice.service.dto.response.UserAuthenticationResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	User toEntity(UserRequestDto request);

	UserAuthenticationResponseDto toAuthDto(User user);
}
