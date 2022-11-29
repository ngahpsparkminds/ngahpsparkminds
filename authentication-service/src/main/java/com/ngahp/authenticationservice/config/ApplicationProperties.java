package com.ngahp.authenticationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "application", ignoreInvalidFields = true)
public class ApplicationProperties {
	private ApplicationSecurity security = new ApplicationSecurity();

	@Getter
	@Setter
	public class ApplicationSecurity {
		private Jwt jwt = new Jwt();
	}

	@Getter
	@Setter
	public class Jwt {
		private Long tokenExpiration;
		private Long renewExpiration;
		private String key;
	}
}
