package com.ngahp.userservice.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import static com.ngahp.userservice.constant.ErrorMessageConstant.*;

import lombok.Data;

@Data
public class UserRequestDto {
	@Size(max = 255, message = SIZE_VALIDATE)
	@NotBlank(message = REQUIRE_VALIDATE)
	private String email;
	private UserDetailRequestDto userDetail;

	@Valid
	@Data
	public class UserDetailRequestDto {
		@Size(max = 255, message = SIZE_VALIDATE)
		@NotBlank(message = REQUIRE_VALIDATE)
		private String phoneNo;

		@Size(max = 255, message = SIZE_VALIDATE)
		@NotBlank(message = REQUIRE_VALIDATE)
		private String firstName;

		@Size(max = 255, message = SIZE_VALIDATE)
		@NotBlank(message = REQUIRE_VALIDATE)
		private String middleName;

		@Size(max = 255, message = SIZE_VALIDATE)
		@NotBlank(message = REQUIRE_VALIDATE)
		private String lastName;

		@Size(max = 255, message = SIZE_VALIDATE)
		@NotBlank(message = REQUIRE_VALIDATE)
		private String fullName;

	}
}
