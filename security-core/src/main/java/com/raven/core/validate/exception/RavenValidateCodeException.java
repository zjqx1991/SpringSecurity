package com.raven.core.validate.exception;

import org.springframework.security.core.AuthenticationException;

public class RavenValidateCodeException extends AuthenticationException {

    public RavenValidateCodeException(String msg) {
        super(msg);
    }
}
