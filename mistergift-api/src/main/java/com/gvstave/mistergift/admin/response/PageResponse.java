package com.gvstave.mistergift.admin.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a serialized paging response.
 *
 * @param <T> The serialized entity type.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    /** The current page. */
    private final Page page;

    /**
     * Constructor.
     *
     * @param page The current page.
     */
    public PageResponse(Page page) {
        this.page = page;
    }

    /**
     * Returns the collection results.
     *
     * @return The results.
     */
    @JsonProperty("content")
    public List getContent() {
        return page.getContent();
    }

    /**
     * Returns the previous and the next page url, if it's possible.
     *
     * @return The links.
     */
    @JsonProperty("links")
    public Map<String, Object> getLinks() {
        if (page.getTotalPages() == 1) {
            return null;
        }

        Map<String, Object> paging = new LinkedHashMap<>();
        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest();

        if (page.hasPrevious()) {
            paging.put("previous", uriBuilder.queryParam("page", page.previousPageable().getPageNumber()));
        }

        if (page.hasNext()) {
            paging.put("next", uriBuilder.replaceQueryParam("page", page.nextPageable().getPageNumber()));
        }

        return paging;
    }

}
