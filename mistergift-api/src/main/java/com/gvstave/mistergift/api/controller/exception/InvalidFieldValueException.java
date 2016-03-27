package com.gvstave.mistergift.api.controller.exception;

/**
 * .
 */
public class InvalidFieldValueException extends Exception {

    /**
     *
     */
    public InvalidFieldValueException() {
        super("A field is invalid, null or empty.");
    }

    /**
     *
     * @param fieldName
     */
    public InvalidFieldValueException(String fieldName) {
        super(String.format("The field %s is invalid, null or empty.", fieldName));
    }

}
