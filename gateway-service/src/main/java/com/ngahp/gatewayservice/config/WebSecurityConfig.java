package com.ngahp.gatewayservice.config;

import static vn.sparkminds.common.util.CollectionUtil.isNotEmpty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.ngahp.gatewayservice.filter.AuthenticationFilter;
import com.ngahp.gatewayservice.security.JWTTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    private final JWTTokenProvider jwtTokenProvider;
    private final JwtConfig jwtConfig;
    private final SecurityProblemSupport problemSupport;
    private final ZuulGatewayProperties gatewayProps;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf()
       .disable()
       .cors()
       .and()
       .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
       .and().exceptionHandling()
       .authenticationEntryPoint(problemSupport)
       .accessDeniedHandler(problemSupport)
       .and()
       .addFilterAfter(
               new AuthenticationFilter(jwtConfig,jwtTokenProvider),
               UsernamePasswordAuthenticationFilter.class
       )
       .authorizeRequests()
       .antMatchers(gatewayProps.getSecurity().getUri().getRestricts().toArray(new String[0])).denyAll()
       .antMatchers(gatewayProps.getSecurity().getUri().getCommons().toArray(new String[0])).permitAll()
       .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers(HttpMethod.GET, "/actuator/health/liveness", "/actuator/health/readiness")
            .antMatchers("/");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = gatewayProps.getCors();
        if (isNotEmpty(configuration.getAllowedOrigins())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/**", configuration);
        }
        return new CorsFilter(source);
    }
}
