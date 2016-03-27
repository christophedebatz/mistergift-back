package com.gvstave.mistergift.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a serialized paging response.
 *
 * @param <T> The serialized entity type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse<T> {

    /** The current page. */
    private final Page page;

    /**
     * Constructor.
     *
     * @param page The current page.
     */
    public PageResponse(Page<T> page) {
        this.page = page;
    }

    /**
     * Returns the collection results.
     *
     * @return The results.
     */
    @JsonProperty("content")
    public List getContent() {
        if (page == null) {
            return new ArrayList<>();
        }

        return page.getContent();
    }

    /**
     * Returns the previous and the next page url, if it's possible.
     *
     * @return The links.
     */
    @JsonProperty("links")
    public Map<String, String> getLinks() {
        if (page == null || page.getTotalPages() == 1) {
            return null;
        }

        Map<String, String > paging = new LinkedHashMap<>();
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

        if (page.hasPrevious()) {
            String previousPageUri = uriBuilder
                    .replaceQueryParam("page", page.getNumber())
                    .toUriString();

            paging.put("prev", previousPageUri);
        }

        if (page.hasNext()) {
            String nextPageUri = uriBuilder
                    .replaceQueryParam("page", page.nextPageable().next().getPageNumber())
                    .toUriString();

            paging.put("next", nextPageUri);
        }

        return paging;
    }

}
