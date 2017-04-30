package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateDeserializer;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateSerializer;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private String id;

    /** The product name. */
    @JsonProperty
    private String name;

    /** The product brand. */
    @JsonProperty
    private String brand;

    /** The product reference code. */
    @JsonProperty
    private String reference;

    /** The product description. */
    @JsonProperty
    private String description;

    /** The product picture. */
    @JsonProperty
    private Long pictureId;

    /** The date of adding. */
    @JsonProperty
    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date date = new Date();

    /** The slug name. */
    @JsonProperty
    private String slug;

    @JsonProperty
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
