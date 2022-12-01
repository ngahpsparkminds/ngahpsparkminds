package com.ngahp.gatewayservice.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.ngahp.gatewayservice.config.JwtConfig;
import com.ngahp.gatewayservice.enums.ResponseContants;
import com.ngahp.gatewayservice.security.JWTPayload;
import com.ngahp.gatewayservice.security.JWTTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter{
	private final JwtConfig jwtConfig;
	private final JWTTokenProvider jwtTokenProvider;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		// Request methods you wish to allow
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
		// Request headers you wish to allow
		response.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,X-Access-Token,XKey,Authorization");
		
		//extract header jwt
		String header = request.getHeader(jwtConfig.getHeader());
		if (StringUtils.isBlank(header)) {
			filterChain.doFilter(request, response);
			return;
		}
		// Validate Jwt token
		String jwtToken = header.replace(jwtConfig.getPrefix(), "").trim();
		ResponseContants responseConstant;
		if (ResponseContants.VALID_TOKEN != (responseConstant = jwtTokenProvider.validateJwtToken(jwtToken))) {
			if (ResponseContants.TOKEN_EXPIRED.equals(responseConstant)
					&& "/account/logout-expired-session".equals(request.getRequestURI())) {
				filterChain.doFilter(request, response);
				return;
			}
			doResponse(response, responseConstant);
			return;
		}
		// Parse JWT token
		try {
			JWTPayload jwtPayload = jwtTokenProvider.extractJwtToken(jwtToken);	
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					jwtPayload.getUserName(), null,
					jwtPayload.getAuthorities().stream()
					.filter(Objects::nonNull)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList()));
			RequestContext ctx = RequestContext.getCurrentContext();
			SecurityContextHolder.getContext().setAuthentication(auth);
			ctx.addZuulRequestHeader("userId", jwtPayload.getUserId().toString());
			ctx.addZuulRequestHeader("username", jwtPayload.getUserName());
		} catch (Exception e) {
			log.error("Error", e);
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}

	private void doResponse(HttpServletResponse response, ResponseContants messageAndStatus) throws IOException {
		Map<String, Object> responseObj = new HashMap<>();
		responseObj.put("status", messageAndStatus.getCode());
		responseObj.put("contentType", MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(new ObjectMapper().writeValueAsString(responseObj));
	}
}
