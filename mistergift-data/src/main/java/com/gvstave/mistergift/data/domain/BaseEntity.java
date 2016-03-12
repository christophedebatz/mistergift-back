package com.gvstave.mistergift.data.domain;

/**
 * Base interface to construct entity.
 *
 * @param <T> the primary key (id) type.
 */
public interface BaseEntity<T> {

    /**
     * Returns the entity id.
     *
     * @return the id.
     */
    T getId();

    /**
     * Sets the entity id.
     *
     * @param id the id.
     */
    void setId(T id);

}
