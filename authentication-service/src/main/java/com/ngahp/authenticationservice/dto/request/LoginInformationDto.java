package com.ngahp.authenticationservice.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.ngahp.authenticationservice.constant.ErrorCodeConstant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginInformationDto {
	@ApiModelProperty(notes = "User email has to be a well-formed email address", example = "maxbit@gmail.com", required = true, position = 0)
	@Email(message = ErrorCodeConstant.REQUIRE_VALIDATE)
	private String email;

	@ApiModelProperty(notes = "User password", example = "abcdxyz", required = true, position = 1)
	@NotNull(message = ErrorCodeConstant.REQUIRE_VALIDATE)
	private String password;
}
