package com.gvstave.mistergift.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the global response object.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    /** The error response. */
    @JsonProperty
    private ErrorResponse error;

    /** The data response. */
    @JsonProperty
    private SuccessResponse data;

    /**
     * Constructor.
     *
     * @param error The error response.
     * @param data  The data response.
     */
    public Response(SuccessResponse data, ErrorResponse error) {
        this.error = error;
        this.data = data;
    }

    /**
     * Returns a response from an error response.
     *
     * @param error The error response.
     * @return The response.
     */
    public static Response withError(ErrorResponse error) {
        return new Response(null, error);
    }

    /**
     * Returns a response from a data response.
     *
     * @param data The data response.
     * @return The response.
     */
    public static Response withData(SuccessResponse data) {
        return new Response(data, null);
    }

    /**
     *
     * @return
     */
    public ErrorResponse getError() {
        return error;
    }

    /**
     *
     * @return
     */
    public SuccessResponse getData() {
        return data;
    }

}
