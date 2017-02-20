package com.gvstave.mistergift.api.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a data response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
public class SuccessResponse {

    /** The response status. */
    @JsonProperty
    private int status;

    /** The payload. */
    @JsonProperty
    private Map<String, Object> payload;

    /**
     * Constructor.
     *
     * @param status The response status.
     */
    public SuccessResponse(int status) {
        this.status = status;
        this.payload = new HashMap<>();
    }

    /**
     * Add a payload to the response.
     *
     * @param entry The payload entry.
     */
    public void addPayload(Map.Entry<String, Object> entry) {
        payload.put(entry.getKey(), entry.getValue());
    }

}
