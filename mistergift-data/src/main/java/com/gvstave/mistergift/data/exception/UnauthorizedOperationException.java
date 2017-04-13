package com.gvstave.mistergift.data.exception;

/**
 * .
 */
public class UnauthorizedOperationException extends RuntimeException {

    /**
     *
     */
    public UnauthorizedOperationException() {
        super("You are not authorized to proceed.");
    }

    /**
     *
     * @param actionName
     */
    public UnauthorizedOperationException(String actionName) {
        super(String.format("Cannot proceed to %s.", actionName));
    }

}
