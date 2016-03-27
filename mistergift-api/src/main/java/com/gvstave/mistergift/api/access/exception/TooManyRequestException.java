package com.gvstave.mistergift.api.access.exception;

public class TooManyRequestException extends Exception {

    /**
     *
     * @param msg
     */
    public TooManyRequestException(String msg) {
        super(msg);
    }
}
