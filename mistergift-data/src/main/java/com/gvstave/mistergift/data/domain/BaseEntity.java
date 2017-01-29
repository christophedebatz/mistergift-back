package com.gvstave.mistergift.data.domain;

import java.io.Serializable;

/**
 * Base interface to construct entity.
 *
 * @param <ID> the primary key (id) type.
 */
public interface BaseEntity<ID> extends Serializable {

    /**
     * Returns the entity id.
     *
     * @return the id.
     */
    ID getId();

    /**
     * Sets the entity id.
     *
     * @param id the id.
     */
    void setId(ID id);

}
