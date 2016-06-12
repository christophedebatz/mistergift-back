package com.gvstave.mistergift.api.access.exception;

public class TooManyRequestException extends Exception {

    /**
     *
     * @param msg
     */
    public TooManyRequestException(String msg) {
        super(msg);
    }

    /**
     *
     * @param timeToWait
     */
    public TooManyRequestException(Long timeToWait) {
        super(String.format("Too many requests. Please try again in %s s.", timeToWait));
    }

}
