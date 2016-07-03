package com.gvstave.mistergift.data.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a gift.
 */
@Entity
@Table(schema = "mistergift", name = "gifts")
public class Gift extends AbstractTimestampableJpaEntity<Long> {

    /** The gift event. */
    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /** The gift product. */
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany
    @JoinTable(name = "users_gifts",
        joinColumns = { @JoinColumn(name = "gift_id", updatable = false) }
    )
    private List<User> owners;

    /**
     * Constructor.
     */
    public Gift() {
        owners = new ArrayList<>();
    }

    /**
     * Returns the attached event.
     *
     * @return The attached event.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the attached event.
     *
     * @param event The attached event.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Returns the gift oswners.
     *
     * @return The gift owners.
     */
    public List<User> getOwners() {
        return owners;
    }

    /**
     * Sets the gift owners.
     *
     * @param owners The gift owners.
     */
    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

}
