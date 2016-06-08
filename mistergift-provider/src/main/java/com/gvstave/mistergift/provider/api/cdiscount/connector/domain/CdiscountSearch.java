package com.gvstave.mistergift.provider.api.cdiscount.connector.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gvstave.mistergift.provider.api.SearchBuilder;

import java.util.List;
import java.util.Objects;

/**
 * Describes a global product search on Cdiscount API.
 */
public class CdiscountSearch {

    /** The API key. */
    @JsonProperty("ApiKey")
    private String apiKey;

    /** The search request. */
    @JsonProperty("SearchRequest")
    private CdiscountSearchRequest searchRequest;

    /**
     * Constructor.
     *
     * @param apiKey The API key.
     */
    public CdiscountSearch(String apiKey) {
        this(apiKey, null);
    }

    /**
     * Constructor with search builder.
     *
     * @param apiKey The API key.
     * @param search The search builder.
     */
    public CdiscountSearch(String apiKey, SearchBuilder.Search search) {
        Objects.requireNonNull(apiKey);
        this.apiKey = apiKey;
        createFromSearch(search);
    }

    /**
     * Sets the search request.
     *
     * @param searchRequest The search request.
     * @return The search.
     */
    public CdiscountSearch withSearchRequest(CdiscountSearchRequest searchRequest) {
        this.searchRequest = searchRequest;
        return this;
    }

    /**
     * Represents the search request that contains both pagination and filter.
     */
    public class CdiscountSearchRequest {

        /** The search keyword. */
        @JsonProperty("Keyword")
        private String keyword;

        /** The search sort. */
        @JsonProperty("SortBy")
        private CdiscountSearchSort sort;

        /** The filter. */
        @JsonProperty
        private CdiscountSearchFilter filter;

        /** The pagination. */
        @JsonProperty("Pagination")
        private CdiscountPagination pagination;

        /**
         * Constructor.
         *
         * @param keyword The search keyword.
         */
        public CdiscountSearchRequest(String keyword) {
            this.keyword = keyword;
        }

        /**
         * Sets the result sort.
         *
         * @param sort The sort.
         * @return The search request.
         */
        public CdiscountSearchRequest withSort(CdiscountSearchSort sort) {
            this.sort = sort;
            return this;
        }

        /**
         * Sets the pagination.
         *
         * @param pagination The pagination.
         * @return The search request.
         */
        public CdiscountSearchRequest withPagination(CdiscountPagination pagination) {
            this.pagination = pagination;
            return this;
        }

        /**
         * Sets the filter.
         *
         * @param filter The filter.
         * @return The search request.
         */
        public CdiscountSearchRequest withFilter(CdiscountSearchFilter filter) {
            this.filter = filter;
            return this;
        }

    }

    public enum CdiscountSearchSort {

        /** Sort by result relevance. */
        RELEVANCE("relevance"),

        /** Sort by product minimum price. */
        MINIMUM_PRICE("minprice"),

        /** Sort by product maximum price. */
        MAXIMUM_PRICE("maxprice"),

        /** Sort by product rating. */
        RATING("rating");

        /** The sort. */
        String sort;

        /**
         * Constructor.
         *
         * @param sort The sort.
         */
        CdiscountSearchSort(String sort) {
            this.sort = sort;
        }

        /**
         * Returns the sort.
         *
         * @return The sort.
         */
        public String getSort() {
            return sort;
        }

    }

    /**
     * Represents the pagination.
     */
    public class CdiscountPagination {

        /** The number of items per page. */
        @JsonProperty("ItemsPerPage")
        private int pageSize;

        /** The current page. */
        @JsonProperty("PageNumber")
        private int currentPage;

        /**
         * Sets the page size.
         *
         * @param pageSize The page size.
         * @return The pagination.
         */
        public CdiscountPagination withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        /**
         * Sets the current page.
         *
         * @param currentPage The current page.
         * @return The pagination.
         */
        public CdiscountPagination withCurrentPage(int currentPage) {
           this.currentPage = currentPage;
            return this;
        }

    }

    /**
     * Represents the main filter that contains sub filter for querying.
     */
    public class CdiscountSearchFilter {

        /** The price filter. */
        @JsonProperty("Price")
        private CdiscountPriceFilter priceFilter;

        /** Whether market places products are retrieved. */
        @JsonProperty("IncludeMarketPlace")
        private boolean includeMarketPlace;

        /** The list of brands for the product. */
        @JsonProperty("Brands")
        private List<String> brands;

        /**
         * Constructor.
         *
         * @param priceFilter The price filter.
         * @return The filter.
         */
        public CdiscountSearchFilter withPriceFilter(CdiscountPriceFilter priceFilter) {
            this.priceFilter = priceFilter;
            return this;
        }

        /**
         * Includes the markets place products into the results.
         *
         * @param includeMarketPlace If market place are included.
         * @return The filter.
         */
        public CdiscountSearchFilter includeMarketPlace(boolean includeMarketPlace) {
            this.includeMarketPlace = includeMarketPlace;
            return this;
        }

        /**
         * Sets the list of brands.
         *
         * @param brands The list of brands.
         * @return The filter.
         */
        public CdiscountSearchFilter withBrands(List<String> brands) {
            this.brands = brands;
            return this;
        }

    }

    /**
     * Represents the price filter for product querying.
     */
    private class CdiscountPriceFilter {

        /** The minimum price. */
        @JsonProperty("Min")
        private int minimum;

        /** The maximum price. */
        @JsonProperty("Max")
        private int maximum;

        /**
         * Constructor.
         *
         * @param minimum The minimum price.
         * @param maximum The maximum price.
         */
        public CdiscountPriceFilter(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

    }

    /**
     * Updates from search.
     *
     * @param search The search.
     */
    private void createFromSearch(SearchBuilder.Search search) {
        CdiscountSearchFilter filter = new CdiscountSearchFilter();
        filter.withBrands(search.getBrands());
        filter.withPriceFilter(new CdiscountPriceFilter(search.getMinimumPrice(), search.getMaximumPrice()));

        CdiscountPagination pagination = new CdiscountPagination();
        pagination.withCurrentPage(search.getCurrentPage());
        pagination.withPageSize(search.getPageSize());

        this.searchRequest = new CdiscountSearchRequest(search.getQuery());
        this.searchRequest.withFilter(filter);
        this.searchRequest.withSort(null); //todo
        this.searchRequest.withPagination(pagination);
    }

}
