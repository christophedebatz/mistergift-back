package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.domain.jpa.Participation;
import com.gvstave.mistergift.data.domain.jpa.User;

import java.io.Serializable;

/**
 * Represents a user gift participation.
 */
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipationDto implements Serializable {

    /** The serial version UUID. */
    private static final long serialVersionUID = 1L;

    /** The event. */
    private Event event;

    /** The user. */
    private User user;

    /** The gift. */
    private Gift gift;

    /** The participation type. */
    private Participation.ParticipationType type;

    /** The participation value. */
    private Long value;

    /**
     * Default constructor.
     */
    public ParticipationDto() {
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
     * Returns the gift.
     *
     * @return The gift.
     */
    public Gift getGift() {
        return gift;
    }

    /**
     * Sets the gift.
     *
     * @param gift The gift.
     */
    public void setGift(Gift gift) {
        this.gift = gift;
    }

    /**
     * Returns the participation type.
     *
     * @return The participation type.
     */
    public Participation.ParticipationType getType() {
        return type;
    }

    /**
     * Sets the participation type.
     *
     * @param type The participation type.
     */
    public void setType(Participation.ParticipationType type) {
        this.type = type;
    }

    /**
     * Returns the participation value.
     *
     * @return The participation value.
     */
    public Long getValue() {
        return value;
    }

    /**
     * Sets the participation value.
     *
     * @param value The participation value.
     */
    public void setValue(Long value) {
        this.value = value;
    }
}
