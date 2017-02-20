package com.gvstave.mistergift.data.domain.jpa;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(schema = "mistergift", name = "links")
public class Link extends AbstractJpaBaseEntity<Long> {

    /** The associated product. */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /** The buying url. */
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    /**
     * Returns the linked product.
     *
     * @return The linked product.
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * Set the linked product.
     *
     * @param productId The linked product.
     */
    public void setProduct(Long productId) {
        this.productId = productId;
    }

    /**
     * Returns the link url.
     *
     * @return The link url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the link url.
     *
     * @param url The link url.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
