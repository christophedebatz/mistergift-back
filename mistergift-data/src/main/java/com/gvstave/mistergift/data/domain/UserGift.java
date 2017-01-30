package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryInit;

import javax.persistence.EmbeddedId;
import javax.persistence.Table;

/**
 * The user gift (gift that belongs to a user).
 */
@javax.persistence.Entity
@Table(schema = "mistergift", name = "user_gift")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGift implements Entity<UserGiftId> {

    /** The user gift id. */
    @QueryInit({"user", "product"})
    @EmbeddedId
    private UserGiftId id;

    /**
     * Constructor.
     */
    public UserGift () {
        // nothing here
    }

    /**
     * Returns the entity id.
     *
     * @return the id.
     */
    @Override
    public UserGiftId getId () {
        return id;
    }

    /**
     * Sets the entity id.
     *
     * @param id the id.
     */
    @Override
    public void setId (UserGiftId id) {
        this.id = id;
    }
}
