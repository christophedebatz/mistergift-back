package com.gvstave.mistergift.provider.api;

import java.util.List;

/**
 * The product search builder.
 */
public class SearchBuilder {

    /** The search. */
    private Search search;

    /**
     * Creates a new search builder.
     *
     * @return The builder.
     */
    public static SearchBuilder create() {
        return new SearchBuilder();
    }

    /**
     *
     * @param query The query.
     * @return The builder.
     */
    public SearchBuilder withQuery(String query) {
        search.setQuery(query);
        return this;
    }

    /**
     *
     * @param currentPage The current page index.
     * @param pageSize The page size.
     * @return The builder.
     */
    public SearchBuilder withPagination(int currentPage, int pageSize) {
        search.setCurrentPage(currentPage);
        search.setPageSize(pageSize);
        return this;
    }

    /**
     *
     * @param sort The sort.
     * @return The builder.
     */
    public SearchBuilder withSort(SearchSort sort) {
        search.setSort(sort);
        return this;
    }

    /**
     *
     * @param minimum
     * @param maximum
     * @return
     */
    public SearchBuilder withPriceBetween(int minimum, int maximum) {
        search.setMinimumPrice(minimum);
        search.setMaximumPrice(maximum);
        return this;
    }

    /**
     *
     * @param brands
     * @return
     */
    public SearchBuilder withBrands(List<String> brands) {
        search.setBrands(brands);
        return this;
    }

    /**
     *
     * @param includeMarketPlace
     * @return
     */
    public SearchBuilder includeMarketPlace(boolean includeMarketPlace) {
        search.setMarketPlaceIncluded(includeMarketPlace);
        return this;
    }

    /**
     * Returns the search.
     *
     * @return The search.
     */
    public Search build() {
        return search;
    }

    /**
     * Private constructor.
     */
    private SearchBuilder() { }

    /**
     * Represents a generic product search.
     */
    public class Search {

        private String query;

        private int currentPage;

        private int pageSize;

        private SearchSort sort;

        private int minimumPrice;

        private int maximumPrice;

        private List<String> brands;

        private boolean isMarketPlaceIncluded;

        /**
         *
         * @param query
         */
        public void setQuery(String query) {
            this.query = query;
        }

        /**
         *
         * @param currentPage
         */
        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        /**
         *
         * @param pageSize
         */
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         *
         * @param sort
         */
        public void setSort(SearchSort sort) {
            this.sort = sort;
        }

        /**
         *
         * @param minimumPrice
         */
        public void setMinimumPrice(int minimumPrice) {
            this.minimumPrice = minimumPrice;
        }

        /**
         *
         * @param maximumPrice
         */
        public void setMaximumPrice(int maximumPrice) {
            this.maximumPrice = maximumPrice;
        }

        /**
         *
         * @param brands
         */
        public void setBrands(List<String> brands) {
            this.brands = brands;
        }

        /**
         *
         * @param marketPlaceIncluded
         */
        public void setMarketPlaceIncluded(boolean marketPlaceIncluded) {
            isMarketPlaceIncluded = marketPlaceIncluded;
        }

        public String getQuery() {
            return query;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public SearchSort getSort() {
            return sort;
        }

        public int getMinimumPrice() {
            return minimumPrice;
        }

        public int getMaximumPrice() {
            return maximumPrice;
        }

        public List<String> getBrands() {
            return brands;
        }

        public boolean isMarketPlaceIncluded() {
            return isMarketPlaceIncluded;
        }
    }

    /**
     *
     */
    public enum SearchSort {

        RELEVANT,

        RATING,

        PRICE_ASC,

        PRICE_DESC

    }
}


