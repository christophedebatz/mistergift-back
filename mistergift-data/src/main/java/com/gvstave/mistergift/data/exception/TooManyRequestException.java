package com.gvstave.mistergift.data.exception;

import org.springframework.http.HttpStatus;

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

    /**
     *
     * @return
     */
    public static int getStatusCode() {
        return HttpStatus.TOO_MANY_REQUESTS.value();
    }

}
