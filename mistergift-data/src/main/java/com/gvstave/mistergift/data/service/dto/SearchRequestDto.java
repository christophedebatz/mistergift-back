package com.gvstave.mistergift.data.service.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private String reference;

    private String brand;

    public SearchRequestDto() {
        // this is for jackson
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
