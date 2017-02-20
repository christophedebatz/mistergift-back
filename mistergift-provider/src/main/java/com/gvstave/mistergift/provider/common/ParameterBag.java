package com.gvstave.mistergift.provider.common;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;
import java.util.Map;

/**
 * Represents a set of properties.
 */
public class ParameterBag {

    /** The parameters list. */
    private List<NameValuePair> parameters;

    /**
     * Creates a parameter bag with single value.
     *
     * @param key The key.
     * @param value The value.
     * @return A parameter bag.
     */
    public static ParameterBag createSingle(String key, String value) {
        ParameterBag parameterBag = new ParameterBag();
        parameterBag.add(key, value);
        return parameterBag;
    }

    /**
     * Adds a new property.
     *
     * @param key The property key.
     * @param value The property value.
     */
    public void add(String key, String value) {
        parameters.add(new BasicNameValuePair(key, value));
    }

    /**
     * Adds a map as properties.
     *
     * @param values The properties map.
     */
    public void addAll(Map<String, String> values){
        values.entrySet().forEach(
            value -> parameters.add(new BasicNameValuePair(value.getKey(), value.getValue()))
        );
    }

    /**
     * Returns the parameters list.
     *
     * @return The parameters.
     */
    public List<NameValuePair> getParameters() {
        return parameters;
    }

}
