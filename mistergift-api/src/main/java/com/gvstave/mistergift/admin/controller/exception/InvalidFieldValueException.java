package com.gvstave.mistergift.admin.controller.exception;

/**
 * .
 */
public class InvalidFieldValueException extends Exception {

    /**
     *
     */
    public InvalidFieldValueException() {
        super("A field is null or empty.");
    }

    /**
     *
     * @param fieldName
     */
    public InvalidFieldValueException(String fieldName) {
        super(String.format("The %s is null or empty.", fieldName));
    }

}
