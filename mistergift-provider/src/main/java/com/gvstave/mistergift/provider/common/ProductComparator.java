package com.gvstave.mistergift.provider.common;

import com.gvstave.mistergift.provider.domain.Product;

import java.util.Comparator;

/**
 *
 */
public class ProductComparator implements Comparator<Product> {

    /**
     * The sort item.
     */
    public enum SortItem {

        /** The rating. */
        RATING,

        /** The name. */
        NAME,

        /** The price. */
        PRICE;

        /**
         * Returns the {@link SortItem} from a string.
         *
         * @param item The item name.
         * @return The sort item.
         */
        public static SortItem of(String item) {
            for (SortItem sort : SortItem.values()) {
                if (item.equalsIgnoreCase(sort.name())) {
                    return sort;
                }
            }
            return null;
        }
    }

    /**
     * The sort direction.
     */
    public enum SortDirection {

        /** The ascending direction. */
        ASC,

        /** The descending direction. */
        DESC;

        /**
         * Returns the {@link SortDirection} from a string.
         *
         * @param direction The direction name.
         * @return The sort direction.
         */
        public static SortDirection of(String direction) {
            for (SortDirection sort : SortDirection.values()) {
                if (direction.equalsIgnoreCase(sort.name())) {
                    return sort;
                }
            }
            return null;
        }
    }

    /** The sort item. */
    private SortItem sortItem;

    /** The sort direction. */
    private SortDirection sortDirection;

    /**
     *
     * @param sortItem
     * @param sortDirection
     */
    public ProductComparator(SortItem sortItem, SortDirection sortDirection) {
        this.sortItem = sortItem;
        this.sortDirection = sortDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compare(Product first, Product second) {
        int result = 0;

        // by rating
        if (SortItem.RATING == sortItem) {
            if (first.getRating() > second.getRating()) {
                result = 1;
            }
            if (first.getRating() < second.getRating()) {
                result = -1;
            }
        }

        // by name
        if (SortItem.NAME == sortItem) {
            result = first.getName().compareToIgnoreCase(second.getName());
        }

        // by price
        if (SortItem.PRICE == sortItem) {
            if (first.getPrice() > second.getPrice()) {
                result = 1;
            }
            if (first.getPrice() < second.getPrice()) {
                result = 0;
            }
        }

        if (SortDirection.DESC == sortDirection) {
            result = -result;
        }

        return result;
    }
}
