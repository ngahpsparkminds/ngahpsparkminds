package com.ngahp.authenticationservice.exception;

import java.net.URI;

public class UnauthorizeException extends BadRequestException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3162940131178909371L;

	public UnauthorizeException(String messageCode) {
		super(URI.create("unauthorize"), messageCode);
	}

}
