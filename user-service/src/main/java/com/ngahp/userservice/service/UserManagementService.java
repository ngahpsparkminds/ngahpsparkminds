package com.ngahp.userservice.service;

import com.ngahp.userservice.service.dto.request.ResetPasswordRequestDto;
import com.ngahp.userservice.service.dto.request.UserRequestDto;
import com.ngahp.userservice.service.dto.response.UserAuthenticationResponseDto;

public interface UserManagementService {
	void addUser(UserRequestDto dto);

	void resetPassword(ResetPasswordRequestDto dto);

	UserAuthenticationResponseDto getUserAuthInformation(String email);
}
