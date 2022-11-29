package com.ngahp.userservice.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngahp.userservice.service.UserManagementService;
import com.ngahp.userservice.service.dto.request.ResetPasswordRequestDto;
import com.ngahp.userservice.service.dto.request.UserRequestDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-management")
public class UserPrivateController {
	private final UserManagementService userManagementService;

	@ApiOperation(value = "API to add new user to system")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PostMapping(value = "/users")
	public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDto dto) {
		userManagementService.addUser(dto);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "API to reset user password")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 400, message = "Bad request") })
	@PutMapping(value = "/users/password")
	public ResponseEntity<Void> resetUserPassword(@RequestBody ResetPasswordRequestDto dto) {
		userManagementService.resetPassword(dto);
		return ResponseEntity.noContent().build();
	}
}
