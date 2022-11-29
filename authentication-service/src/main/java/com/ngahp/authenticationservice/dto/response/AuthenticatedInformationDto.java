package com.ngahp.authenticationservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticatedInformationDto {
	private String token;
	private String refreshToken;
}
