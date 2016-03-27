package com.gvstave.mistergift.admin.access.exception;

public class TooManyRequestException extends Exception {

    /**
     *
     * @param msg
     */
    public TooManyRequestException(String msg) {
        super(msg);
    }
}
