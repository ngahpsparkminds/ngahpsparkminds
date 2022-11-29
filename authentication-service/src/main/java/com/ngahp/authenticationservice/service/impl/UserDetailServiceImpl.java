package com.ngahp.authenticationservice.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ngahp.authenticationservice.dto.response.UserAuthenticationResponseDto;
import com.ngahp.authenticationservice.feign.UserClient;
import com.ngahp.authenticationservice.service.mapper.UserDetailMapper;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("customUserDetailService")
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
	private final UserClient userClient;
	private final UserDetailMapper userDetailMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			UserAuthenticationResponseDto response = userClient.getUserAuthInformation(username).getBody();
			return userDetailMapper.toUserDetail(response);
		} catch (FeignClientException e) {
			log.info("Error when call feign get user infor {}", e);
			throw new UsernameNotFoundException("User not found by email {}", e);
		}
	}

}
