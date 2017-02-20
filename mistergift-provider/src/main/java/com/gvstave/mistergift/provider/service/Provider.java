package com.gvstave.mistergift.provider.service;

/**
 * Represents a product provider.
 */
public enum Provider {

    CDISCOUNT("cdiscount"),

    AMAZON("amazon"),

    FNAC("fnac"),

    DARTY("darty");

    /**
     * The key.
     */
    String key;

    /**
     * Default constructor.
     *
     * @param key The key.
     */
    Provider(String key) {
        this.key = key;
    }

    /**
     * Returns the key.
     *
     * @return The key.
     */
    String getKey() {
        return key;
    }

}
