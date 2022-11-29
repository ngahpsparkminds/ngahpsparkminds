package com.ngahp.authenticationservice.controller.error;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import com.ngahp.authenticationservice.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseExceptionHandling implements ProblemHandling, SecurityAdviceTrait {
	private final MessageSource messageSource;

	@Override
	public void log(final Throwable throwable, final Problem problem, final NativeWebRequest request,
			final HttpStatus status) {
		if (status.is4xxClientError()) {
			LOG.warn("{}", String.format("%s:%s", status.getReasonPhrase(), throwable.getMessage()), throwable);
		} else if (status.is5xxServerError()) {
			LOG.error(status.getReasonPhrase(), throwable);
		}
	}

	@Override
	public ResponseEntity<Problem> process(ResponseEntity<Problem> entity, NativeWebRequest request) {
		if (entity == null) {
			return null;
		}

		Problem problem = entity.getBody();
		if (!(problem instanceof BadRequestException)) {
			return entity;
		}
		ProblemBuilder builder = Problem.builder();
		if (problem instanceof BadRequestException) {
			String messageCode = problem.getTitle();
			builder.withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? null : problem.getType());
			builder.withDetail(getMessageSource(messageCode));
			builder.with("messageCode", messageCode);
		}
		return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
	}

	private String getMessageSource(String messageCode) {
		return messageSource.getMessage(messageCode, null, messageCode, Locale.forLanguageTag("en-US"));
	}
}
