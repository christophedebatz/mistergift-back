package com.debatz.mistergift.admin.controller.exception;

public class UnauthorizedException extends Exception {

    /**
     *
     */
    public UnauthorizedException() {
        super("Bad credentials.");
    }
}
