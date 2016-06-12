package com.gvstave.mistergift.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents an response in failure.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /** The error status. */
    @JsonProperty
    private int status;

    /** The error exception. */
    @JsonProperty
    private String exception;

    /** The error message. */
    @JsonProperty
    private String message;

    /**
     * Constructor.
     *
     * @param status The status code.
     * @param exception The exception.
     * @param message The message.
     */
    public ErrorResponse(int status, String exception, String message) {
        this.status = status;
        this.exception = exception;
        this.message = message;
    }

    /**
     * Constructor.
     *
     * @param status The status code.
     */
    public ErrorResponse(int status) {
        this.status = status;
    }

    /**
     * Returns an error response from an exception.
     *
     * @param exception The exceptin.
     * @param status The status code.
     * @return The {@link ErrorResponse}.
     */
    public static ErrorResponse fromException(Exception exception, int status) {
        Objects.requireNonNull(exception);
        return new ErrorResponse(status, exception.getClass().getSimpleName(), exception.getMessage());
    }

    /**
     * Returns the status code.
     *
     * @return The status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the exception.
     *
     * @return The exception.
     */
    public String getException() {
        return exception;
    }

    /**
     * Returns the message.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the status code.
     *
     * @param status The status code.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Sets the exception.
     *
     * @param exception The exception.
     */
    public void setException(String exception) {
        this.exception = exception;
    }

    /**
     * Sets the message.
     *
     * @param message The message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
