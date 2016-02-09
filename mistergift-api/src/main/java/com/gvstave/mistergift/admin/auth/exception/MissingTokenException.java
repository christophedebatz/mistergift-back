package com.gvstave.mistergift.admin.auth.exception;


public class MissingTokenException extends Exception {

    public MissingTokenException() {
        super("No token has been found.");
    }

}
