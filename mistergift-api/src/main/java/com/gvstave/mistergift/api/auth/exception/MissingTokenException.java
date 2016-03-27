package com.gvstave.mistergift.api.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 */
public class MissingTokenException extends AuthenticationException {

    /**
     * Constructor.
     */
    public MissingTokenException() {
        super("Missing authorization token.");
    }
}
