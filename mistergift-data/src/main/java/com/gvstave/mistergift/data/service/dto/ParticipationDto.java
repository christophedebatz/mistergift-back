package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.domain.jpa.Participation;
import com.gvstave.mistergift.data.domain.jpa.User;

import java.io.Serializable;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Event event;

    private User user;

    private Gift gift;

    private Participation.ParticipationType type;

    private Long value;

    public ParticipationDto() {
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

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Participation.ParticipationType getType() {
        return type;
    }

    public void setType(Participation.ParticipationType type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
