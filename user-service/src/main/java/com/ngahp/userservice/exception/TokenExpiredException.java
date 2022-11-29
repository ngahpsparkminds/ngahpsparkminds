package com.ngahp.userservice.exception;

import java.net.URI;

public class TokenExpiredException extends BadRequestException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5899904917167546528L;

	public TokenExpiredException(String messageCode) {
		super(URI.create("data-expired"), messageCode);
	}

}
