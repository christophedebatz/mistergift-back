package com.gvstave.mistergift.provider.domain;

import com.gvstave.mistergift.provider.service.Provider;

/**
 * Describes a product.
 */
public class RemoteProduct {

    /** The provider. */
    private Provider provider;

    /** The product name. */
    private String name;

    /** The api. */
    private Api api;

    /** The api id. */
    private String apiId;

    /** The product brand. */
    private String brand;

    /** The product description. */
    private String description;

    /** The product price. */
    private double price;

    /** The product rating. */
    private float rating;

    /** The product picture url. */
    private String pictureUrl;

    /** The product seller name. */
    private String seller;

    /** The product shipping fees. */
    private double shippingFees;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public double getShippingFees() {
        return shippingFees;
    }

    public void setShippingFees(double shippingFees) {
        this.shippingFees = shippingFees;
    }
}
