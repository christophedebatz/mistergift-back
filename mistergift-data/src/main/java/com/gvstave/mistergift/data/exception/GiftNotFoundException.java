package com.gvstave.mistergift.data.exception;

/**
 * Exception that is thrown when input data is not valid or incomplete.
 */
public class GiftNotFoundException extends RuntimeException {

    /**
     * Constructor without details.
     */
    public GiftNotFoundException() {
        super("The given product has been not found.");
    }

    /**
     * Constructor.
     *
     * @param giftId The invalid field name.
     */
    public GiftNotFoundException(Long giftId) {
        super(String.format("The gift with id={} has been not found.", giftId));
    }

}
