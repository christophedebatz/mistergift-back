package com.gvstave.mistergift.admin.auth.exception;

/**
 *
 */
public class MissingTokenException extends Exception {

    /**
     * Constructor.
     */
    public MissingTokenException() {
        super("Missing authorization token.");
    }
}
