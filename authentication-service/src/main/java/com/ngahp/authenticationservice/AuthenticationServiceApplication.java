package com.ngahp.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ngahp.authenticationservice.config.ApplicationProperties;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({ApplicationProperties.class})
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

}
