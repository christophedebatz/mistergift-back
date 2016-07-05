package com.gvstave.mistergift.api.controller.exception;

/**
 * When there is an entity duplicate attempt.
 */
public class DuplicatedEntityException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public DuplicatedEntityException(String entityName, String field) {
        super("The entity " + entityName + " already exists for field " + field);
    }

    /**
     * {@inheritDoc}
     */
    public DuplicatedEntityException() {
        super("The given entity already exist.");
    }

}
