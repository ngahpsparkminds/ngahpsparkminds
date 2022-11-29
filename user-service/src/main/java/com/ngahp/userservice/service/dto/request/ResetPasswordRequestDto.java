package com.ngahp.userservice.service.dto.request;

import static com.ngahp.userservice.constant.ErrorMessageConstant.REQUIRE_VALIDATE;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {
	@NotBlank(message = REQUIRE_VALIDATE)
	private String token;

	@NotBlank(message = REQUIRE_VALIDATE)
	private String password;
}
