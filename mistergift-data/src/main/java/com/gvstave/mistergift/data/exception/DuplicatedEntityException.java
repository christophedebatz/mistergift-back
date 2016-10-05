package com.gvstave.mistergift.data.exception;

/**
 * When there is an entity duplicate attempt.
 */
public class DuplicatedEntityException extends RuntimeException {

    /**
     *
     * @param entityName
     * @param field
     */
    public DuplicatedEntityException(String entityName, String field) {
        super("The entity " + entityName.toLowerCase() + " already exists for field " + field.toLowerCase());
    }

    /**
     *
     */
    public DuplicatedEntityException() {
        super("The given entity already exist.");
    }

}
