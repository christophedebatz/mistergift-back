package com.gvstave.mistergift.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a response in failure.
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

    /** The error optional details (to help error understanding front-side). */
    @JsonProperty
    private Map<String, Object> details;

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
     * Adds a parameter.
     *
     * @param key The parameter key.
     * @param value The parameter value.
     */
    public void withDetail (String key, Object value) {
        Objects.requireNonNull(key);
        if (details == null) {
            details = new HashMap<>();
        }

        String newValue = Optional.ofNullable(details.get(key))
            .map(oldValue -> String.format("%s,%s", oldValue, value))
            .orElse(value.toString());

        details.put(key, newValue);
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
