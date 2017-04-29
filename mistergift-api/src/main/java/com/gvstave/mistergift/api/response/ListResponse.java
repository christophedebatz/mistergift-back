package com.gvstave.mistergift.api.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a list response.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
public class ListResponse<T extends Serializable> {

    /** The payload. */
    @JsonProperty
    private List<T> content;

    /**
     * Constructor.
     *
     * @param content The response content.
     */
    public ListResponse(List<T> content) {
        this.content = content;
    }

    /**
     * Empty constructor.
     */
    public ListResponse() {
        // this is for jackson
    }

    /**
     *
     * @return
     */
    public List<T> getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(List<T> content) {
        this.content = content;
    }
}
