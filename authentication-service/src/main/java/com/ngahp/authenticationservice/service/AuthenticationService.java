package com.ngahp.authenticationservice.service;

import com.ngahp.authenticationservice.dto.request.LoginInformationDto;
import com.ngahp.authenticationservice.dto.response.AuthenticatedInformationDto;

public interface AuthenticationService {
    AuthenticatedInformationDto authenticate(LoginInformationDto loginDto);
}
