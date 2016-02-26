package com.gvstave.mistergift.admin.controller.exception;

/**
 * .
 */
public class UnauthorizedOperationException extends Exception {

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
