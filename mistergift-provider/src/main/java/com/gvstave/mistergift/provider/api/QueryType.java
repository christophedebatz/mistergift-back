package com.gvstave.mistergift.provider.api;

/**
 *
 */
public enum QueryType {

    Search("Search");

    String key;

    QueryType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}