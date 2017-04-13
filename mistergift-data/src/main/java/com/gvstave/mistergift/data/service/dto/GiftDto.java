package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;

import java.io.Serializable;

/**
 * The gift dto.
 */
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftDto implements Serializable {

    /** The serial version UUID. */
    private static final long serialVersionUID = 1L;

    /** The event. */
    private Event event;

    /** The user. */
    private User user;

    /** The product id. */
    private String productId;

    /**
     * Constructor.
     */
    public GiftDto() {
        // this is for jackson
    }

    /**
     * Returns the event.
     *
     * @return The event.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event.
     *
     * @param event The event.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Returns the user.
     *
     * @return The user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user The user.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the product id.
     *
     * @return The product id.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the product id.
     *
     * @param productId The product id.
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }
}
