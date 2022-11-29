package com.ngahp.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngahp.userservice.service.UserManagementService;
import com.ngahp.userservice.service.dto.response.UserAuthenticationResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/")
public class UserInternalController {
	private final UserManagementService userManagementService;

	@ApiOperation(value = "API for get user login information")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal server error"),
			@ApiResponse(code = 400, message = "Bad request") })
	@GetMapping("/users/{email}")
	public ResponseEntity<UserAuthenticationResponseDto> getUserAuthInformation(@PathVariable String email) {
		return ResponseEntity.ok(userManagementService.getUserAuthInformation(email));
	}
}
