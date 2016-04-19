package com.gvstave.mistergift.provider.domain;

/**
 * Created by christophedebatz on 19/04/2016.
 */
public enum Api {

    /**
     * The Cdiscount api.
     */
    Cdiscount("Cdiscount"),

    /**
     * The Amazon api.
     */
    Amazon("Amazon"),

    /**
     * The Darty api.
     */
    Darty("Darty"),

    /**
     * The Fnac api.
     */
    Fnac("Fnac");

    /**
     * The key.
     */
    String key;

    /**
     * Constructor.
     *
     * @param key The key.
     */
    Api(String key) {
        this.key = key;
    }

    /**
     * Returns the key.
     *
     * @return The key.
     */
    public String getKey() {
        return key;
    }

}
