package com.ngahp.authenticationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ngahp.authenticationservice.dto.response.UserAuthenticationResponseDto;

@FeignClient(name ="user-service", url =  "${client.services.user-service}")
public interface UserClient {
	@GetMapping(value =  "/internal/users/{email}")
	public ResponseEntity<UserAuthenticationResponseDto> getUserAuthInformation(@PathVariable String email);
}
