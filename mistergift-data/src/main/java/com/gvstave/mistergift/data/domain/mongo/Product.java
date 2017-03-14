package com.gvstave.mistergift.data.domain.mongo;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Represents a global product (such as Sony Ps4 or Canapé Cuir :-) )
 */
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    /** The product name. */
    @Field
    private String name;

    /** The product brand. */
    @Field
    private String brand;

    /** The product reference code. */
    @Field
    private String reference;

    /** The product description. */
    @Field
    private String description;

    /** The product picture. */
    @Field
    private Long pictureId;

    /**
     * Hibernate constructor.
     */
    public Product() {
        // nothing
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
    public Long getPicture() {
        return pictureId;
    }

    /**
     * Set the gift picture id.
     *
     * @param pictureId The gift picture id.
     */
    public void setPicture(Long pictureId) {
        this.pictureId = pictureId;
    }

}
