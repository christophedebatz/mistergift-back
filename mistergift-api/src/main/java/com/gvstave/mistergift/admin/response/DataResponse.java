package com.gvstave.mistergift.admin.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a data response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
public class DataResponse {

    /** The response status. */
    @JsonProperty
    private int status;

    /** The payload. */
    @JsonProperty
    private List<Map<String, Object>> payload;

    /**
     * Constructor.
     *
     * @param status The response status.
     */
    public DataResponse(int status) {
        this.status = status;
        this.payload = new ArrayList<>();
    }

    /**
     * Add a payload to the response.
     *
     * @param payload The payload.
     */
    public void addPayload(Map<String, Object> payload) {
        this.payload.add(payload);
    }

}
