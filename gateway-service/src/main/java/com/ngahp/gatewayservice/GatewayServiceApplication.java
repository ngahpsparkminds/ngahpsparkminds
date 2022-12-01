package com.ngahp.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ngahp.gatewayservice.config.ZuulGatewayProperties;

@SpringBootApplication
@EnableConfigurationProperties({ZuulGatewayProperties.class})
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}
