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
     * @throws Exception If the operation failed.
     */
    void set(String key, Object value) throws Exception;

    /**
     * Sets a new value with ttl.
     *
     * @param key   The value key.
     * @param value The value.
     * @param ttl   The ttl.
     * @throws Exception If the operation failed.
     */
    void set(String key, Object value, Long ttl) throws Exception;

    /**
     * Returns the value associated with the given key.
     *
     * @param key The key.
     * @return The value.
     *
     * @throws Exception If the operation failed.
     */
    Object get(String key) throws Exception;

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
