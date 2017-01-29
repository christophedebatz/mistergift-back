package com.gvstave.mistergift.data.cache;

import java.util.List;

/**
 * Cache service interface.
 * Provides some useful cache methods.
 */
public interface CacheService {

    /**
     * Returns all the stored keys.
     *
     * @return The keys.
     */
    List<String> getKeys();

    /**
     * Returns a record TTL (time to leave).
     *
     * @param key The key.
     * @return The TTL.
     */
    Long getTTL(String key);

    /**
     * Delete values that are associated to the given keys.
     *
     * @param keys The keys to remove.
     * @return If any data has been removed.
     */
    boolean delete(String... keys);

    /**
     * Sets a new value.
     *
     * @param key   The value key.
     * @param value The value.
     */
    void set(String key, Object value);

    /**
     * Sets a new value with ttl.
     *
     * @param key   The value key.
     * @param value The value.
     * @param ttl   The ttl.
     */
    void set(String key, Object value, Long ttl);

    /**
     * Returns the value associated with the given key.
     *
     * @param key The key.
     * @return The value.
     */
    Object get(String key);

    /**
     * Returns the value associated to the given key or the default value
     * if the key does not exists.
     *
     * @param key          THe key.
     * @param defaultValue The default value if key not found.
     * @return The value.
     */
    Object get(String key, Object defaultValue);

    /**
     * @param key
     * @return
     */
    boolean exists(String key);

}
