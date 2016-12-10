package com.gvstave.mistergift.api.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * .
 */
public class UserNotFoundException extends AuthenticationException {

    /**
     * Constructor.
     */
    public UserNotFoundException() {
        super("User has been not found.");
    }
}


