package com.gvstave.mistergift.data.domain;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "links")
public class Link extends AbstractJpaEntity<Long>
{
    /** The associated product. */
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    /**
     * Returns the linked product.
     *
     * @return The linked product.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the linked product.
     *
     * @param product The linked product.
     */
    public void setProduct(Product product) {
        this.product = product;
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
