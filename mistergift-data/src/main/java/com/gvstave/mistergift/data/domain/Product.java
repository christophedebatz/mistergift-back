package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "mistergift", name = "products")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends AbstractTimestampableJpaEntity<Long> {

    /** The product name. */
    @Column(name = "name", length = 75, nullable = false)
    private String name;

    /** The product brand. */
    @Column(name = "brand", length = 75, nullable = false)
    private String brand;

    /** The product reference code. */
    @Column(name = "reference", length = 25)
    private String reference;

    /** The product description. */
    @Lob
    @Column(name = "description")
    private String description;

    /** The product picture. */
    @OneToOne
    @JoinColumn(name = "picture_id")
    private FileMetadata picture;

    /**
     * Hibernate constructor.
     */
    public Product() {
        // nothing
    }

    /**
     * Returns the gift name.
     *
     * @return The gift name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the gift name.
     *
     * @param name The gift name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the gift brand.
     *
     * @return The gift brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the gift brand.
     *
     * @param brand The gift brand.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the gift reference.
     *
     * @return The gift reference.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Set the gift reference.
     *
     * @param reference The gift reference.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Returns the gift description.
     *
     * @return The gift description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the gift description.
     *
     * @param description The gift description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the gift picture.
     *
     * @return The gift picture.
     */
    public FileMetadata getPicture() {
        return picture;
    }

    /**
     * Set the gift picture.
     *
     * @param picture The gift picture.
     */
    public void setPicture(FileMetadata picture) {
        this.picture = picture;
    }

}
