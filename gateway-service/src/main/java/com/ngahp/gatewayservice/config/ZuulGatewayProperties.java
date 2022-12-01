package com.ngahp.gatewayservice.config;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import vn.sparkminds.common.config.ApplicationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "application")
public class ZuulGatewayProperties extends ApplicationProperties {
    private final CustomSecurity security = new CustomSecurity();

    @Getter
    @Setter
    public static class CustomSecurity extends Security {
        private SecurityUri uri;
        private boolean enableCustomVoter;
        private Cache cache;
    }

    @Getter
    @Setter
    public static class SecurityUri {
        private Set<String> restricts;
        private PublicRequest publics;
        private Set<String> privates;
        private Set<String> commons;
        private Set<ApiWithMethod> commonPrivates;
        private Set<ApiWithMethod> noneTwoFaPrivates;
    }

    @Getter
    @Setter
    public static class Cache {
        private CachePermission permissions;
    }

    @Getter
    @Setter
    public static class CachePermission {
        private long expirationInMillis;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class ApiWithMethod {
        private String uri;
        private Set<String> method;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class PublicRequest {
        private Set<String> unverified;
        private Set<ApiWithMethod> verified;
    }
}
