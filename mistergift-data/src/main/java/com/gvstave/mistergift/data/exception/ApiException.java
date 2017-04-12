package com.gvstave.mistergift.data.exception;


import org.springframework.http.HttpStatus;

/**
 * When there is an api exception
 */
public class ApiException extends RuntimeException {

    private HttpStatus status;

    /**
     * Constructor.
     *
     * @param message The error message.
     * @param status The status code.
     */
    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
