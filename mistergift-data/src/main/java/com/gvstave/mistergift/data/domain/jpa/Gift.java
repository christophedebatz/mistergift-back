package com.gvstave.mistergift.data.domain.jpa;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a gift.
 */
@Entity
@Table(schema = "mistergift", name = "gifts")
public class Gift extends AbstractTimestampableJpaBaseEntity<Long> {

    /** The gift event. */
    @OneToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    /** The gift product id. */
    @Column(name = "product_id", nullable = false)
    private String productId;

    /** The users who have booked the gift. */
    @OneToMany
    @JoinTable(
        name = "users_gifts",
        joinColumns = { @JoinColumn(name = "gift_id", updatable = false) }
    )
    private List<User> bookers;

    /**
     * Constructor.
     */
    public Gift() {
        bookers = new ArrayList<>();
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
    public List<User> getBookers() {
        return bookers;
    }

    /**
     * Sets the gift owners.
     *
     * @param owners The gift owners.
     */
    public void setBookers(List<User> owners) {
        this.bookers = owners;
    }

    /**
     * Returns the product id.
     *
     * @return The product id.
     */
    public String getProductId () {
        return productId;
    }

    /**
     * Sets the product id.
     *
     * @param productId The product id.
     */
    public void setProductId (String productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     */
    public User getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

}
