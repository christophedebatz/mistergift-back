package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mysema.query.annotations.QueryInit;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "mistergift", name = "whishlists")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Whishlist implements BaseEntity<WhishlistId> {

    @QueryInit({"user", "product"})
    @EmbeddedId
    private WhishlistId id;

    public Whishlist() {
        // nothing here
    }

    /**
     * Returns the entity id.
     *
     * @return the id.
     */
    @Override
    public WhishlistId getId () {
        return id;
    }

    /**
     * Sets the entity id.
     *
     * @param id the id.
     */
    @Override
    public void setId (WhishlistId id) {
        this.id = id;
    }
}
