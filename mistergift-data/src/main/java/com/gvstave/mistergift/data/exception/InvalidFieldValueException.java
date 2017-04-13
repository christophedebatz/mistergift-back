package com.gvstave.mistergift.data.exception;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Exception that is thrown when input data is not valid or incomplete.
 */
public class InvalidFieldValueException extends RuntimeException {

    /** The fields that are not valid. */
    private Set<String> fields;

    /**
     * Constructor without details.
     */
    public InvalidFieldValueException() {
        super("A field is invalid, null or empty.");
    }

    /**
     * Constructor.
     *
     * @param fieldName The invalid field name.
     */
    public InvalidFieldValueException(String fieldName) {
        super(String.format("The field %s seem to be invalid. Please fix it.", fieldName));
        if (fields == null) {
            fields = new HashSet<>();
        }
        fields.add(fieldName);
    }

    /**
     * Constructor.
     *
     * @param fieldsNames The invalid fields names.
     */
    public InvalidFieldValueException(List<String> fieldsNames) {
        super("Some fields seems to be invalid. Please fix them.");
        if (fields == null) {
            fields = new HashSet<>();
        }
        fields.addAll(fieldsNames);
    }

    /**
     * Returns the invalid fields names.
     *
     * @return The fields names.
     */
    public Set<String> getFields () {
        return fields;
    }

}
