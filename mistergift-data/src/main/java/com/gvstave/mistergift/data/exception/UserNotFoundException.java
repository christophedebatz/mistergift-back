package com.gvstave.mistergift.data.exception;

/**
 * When there is an entity duplicate attempt.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     *
     */
    public UserNotFoundException() {
        super("The user has been not found.");
    }

}
