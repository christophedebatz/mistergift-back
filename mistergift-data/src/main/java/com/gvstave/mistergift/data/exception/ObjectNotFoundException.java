package com.gvstave.mistergift.data.exception;

/**
 * Exception that is thrown when input data is not valid or incomplete.
 */
public class ObjectNotFoundException extends RuntimeException {

    /**
     * Constructor without details.
     */
    public ObjectNotFoundException() {
        super("The given object has been not found.");
    }

    /**
     * Constructor.
     * @param objectTypeName The type of the object that cause the error.
     * @param objectId       The id of the object.
     */
    public ObjectNotFoundException(String objectTypeName, Long objectId) {
        super(String.format("The {} with id={} has been not found.", objectTypeName, objectId));
    }

}
