package com.gvstave.mistergift.data.exception;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Exception that is thrown when input data is not valid or incomplete.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructor without details.
     */
    public ProductNotFoundException() {
        super("The given product has been not found.");
    }

    /**
     * Constructor.
     *
     * @param productId The invalid field name.
     */
    public ProductNotFoundException(String productId) {
        super(String.format("The given productId has been not found.", productId));
    }

}
