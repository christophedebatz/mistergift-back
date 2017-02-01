package com.gvstave.mistergift.data.domain.jpa;

import com.querydsl.core.annotations.QueryEmbeddable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * The embedded id for {@link UserEvent}.
 */
@Embeddable
@QueryEmbeddable
public class UserEventId implements Serializable {

    /** The event. */
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /** The user. */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Hibernate constructor.
     */
    public UserEventId() {
        // nothing
    }

    /**
     * Constructor.
     *
     * @param event The event.
     * @param user  The user.
     */
    public UserEventId(Event event, User user) {
        this.event = event;
        this.user = user;
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

}


