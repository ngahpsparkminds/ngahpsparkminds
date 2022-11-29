package com.ngahp.authenticationservice.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngahp.authenticationservice.dto.request.LoginInformationDto;
import com.ngahp.authenticationservice.dto.response.AuthenticatedInformationDto;
import com.ngahp.authenticationservice.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class AuthenticationController {
	private final AuthenticationService authService;

	@PostMapping(value = "/auth")
	public ResponseEntity<AuthenticatedInformationDto> authenticateAdmin(
			@RequestBody @Valid LoginInformationDto loginInformation) {
		// Authenticate user
		AuthenticatedInformationDto authenticatedInformation = authService.authenticate(loginInformation);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + authenticatedInformation.getToken());
		return new ResponseEntity<>(authenticatedInformation, headers, HttpStatus.OK);
	}
}
