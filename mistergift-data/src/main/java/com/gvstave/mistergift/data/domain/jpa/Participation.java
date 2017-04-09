package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.service.dto.ParticipationDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user gift participation.
 */
@Entity
@Table(schema = "mistergift", name = "participations")
public class Participation extends AbstractTimestampableJpaBaseEntity<Long> {

    /** The participation type. */
    public enum ParticipationType {

        /** an absolute participation. */
        ABSOLUTE("fixed"),

        /** a relative participation. */
        RELATIVE("mixed");

        /** The type name. */
        String type;

        public String getType() {
            return type;
        }

        ParticipationType(String type) {
            this.type = type;
        }
    }

    /** The gift event. */
    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToOne
    @JoinColumn(name = "gift_id", nullable = false)
    private Gift gift;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ParticipationType type;

    /**
     * Constructor.
     */
    public Participation() {
        // for the players
    }

    /**
     * Copy constructor.
     *
     * @param dto The dto to copy.
     */
    public Participation(ParticipationDto dto) {
        setGift(dto.getGift());
        setEvent(dto.getEvent());
        setUser(dto.getUser());
    }

    /**
     * Rturns the event.
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

    public ParticipationType getType() {
        return type;
    }

    public void setType(ParticipationType type) {
        this.type = type;
    }
}
