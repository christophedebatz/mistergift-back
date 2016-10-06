package com.gvstave.mistergift.data.domain;

import com.mysema.query.annotations.QueryEmbeddable;

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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public WhishlistId() {
        // Hibernate constructor
    }

    public WhishlistId (User user, Product product) {
        this.user = user;
        this.product = product;
    }

    public User getUser () {
        return user;
    }

    public Product getProduct () {
        return product;
    }
}
