package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;

import java.io.Serializable;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Event event;

    private User user;

    private String productId;

    public GiftDto() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
