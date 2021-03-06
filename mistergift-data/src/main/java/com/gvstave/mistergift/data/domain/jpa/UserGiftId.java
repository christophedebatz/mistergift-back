package com.gvstave.mistergift.data.domain.jpa;


import com.querydsl.core.annotations.QueryEmbeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * The embedded id for {@link UserGift}.
 */
@Embeddable
@QueryEmbeddable
public class UserGiftId implements Serializable {

    /** The gift owner. */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The associated product id. */
    @Column(name = "product_id", nullable = false)
    private String productId;

    /**
     * Constructor
     */
    public UserGiftId () {
        // Hibernate constructor
    }

    /**
     * Constructor.
     *
     * @param user The user.
     * @param productId The product id.
     */
    public UserGiftId (User user, String productId) {
        this.user = user;
        this.productId = productId;
    }

    /**
     * Returns the user.
     *
     * @return The user.
     */
    public User getUser () {
        return user;
    }

    /**
     * Returns the product id.
     *
     * @return The product id.
     */
    public String getProductId () {
        return productId;
    }

}
