package com.ngahp.userservice.exception;

import java.net.URI;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String messageCode) {
        this(null, messageCode);
    }

    public BadRequestException(URI type, String messageCode) {
        super(type, messageCode, Status.BAD_REQUEST, null, null, null, null);
    }

}
