package com.ngahp.userservice.service.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ngahp.userservice.service.EmailTokenService;
import com.ngahp.userservice.service.event.CreateUserEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateUserEventListener {
	private final EmailTokenService emailTokenService;

	@EventListener
	public void saveEmailToken(CreateUserEvent event) {
		log.info("Start save email token");
		emailTokenService.generateEmailToken(event.getUser(), event.getToken(), event.getTokenType(),
				event.getExpiration());
	}
}
