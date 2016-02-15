package com.gvstave.mistergift.admin.response;

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
    private DataResponse data;

    /**
     * Constructor.
     *
     * @param error The error response.
     * @param data The payloaded response.
     */
    public Response(DataResponse data, ErrorResponse error) {
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
    public static Response withData(DataResponse data) {
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
    public DataResponse getData() {
        return data;
    }

}
