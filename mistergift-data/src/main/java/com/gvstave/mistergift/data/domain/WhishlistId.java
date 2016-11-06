package com.gvstave.mistergift.data.domain;

import com.mysema.query.annotations.QueryEmbeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * The embedded id for {@link Whishlist}.
 */
@Embeddable
@QueryEmbeddable
public class WhishlistId implements Serializable {

    /** The whishlist owner. */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** The associated product id. */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * Constructor
     */
    public WhishlistId() {
        // Hibernate constructor
    }

    /**
     * Constructor.
     *
     * @param user The user.
     * @param productId The product id.
     */
    public WhishlistId (User user, Long productId) {
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
    public Long getProductId () {
        return productId;
    }

}
