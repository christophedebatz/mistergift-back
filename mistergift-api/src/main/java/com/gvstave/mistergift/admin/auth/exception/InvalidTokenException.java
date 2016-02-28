package com.gvstave.mistergift.admin.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 */
public class InvalidTokenException extends AuthenticationException {

    /**
     * Constructor.
     */
    public InvalidTokenException() {
        super("The authorization token is wrong.");
    }
}
