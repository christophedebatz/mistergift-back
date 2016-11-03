package com.gvstave.mistergift.data.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *
 */
@Document(indexName = "products")
public class Product extends AbstractElasticsearchEntity<Long> {

    /** The product name. */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;

    /** The product brand. */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String brand;

    /** The product reference code. */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String reference;

    /** The product description. */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String description;

    /** The product picture. */
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long pictureId;

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
