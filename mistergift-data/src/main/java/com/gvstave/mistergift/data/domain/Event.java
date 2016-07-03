package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "events")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event extends AbstractTimestampableJpaEntity<Long> {

    /** The event name. */
    @Column(name = "name", length = 75, unique = true, nullable = false)
    private String name;

    /** The event description. */
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    /** The event date. */
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /** The group members. */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_events",
            joinColumns = { @JoinColumn(name = "event_id", nullable = false, updatable = false) }
    )
    private List<UserEvent> userEvents;

    /**
     * Constructor.
     */
    public Event() {
        this.userEvents = new ArrayList<>();
    }

    /**
     * Returns the group name.
     *
     * @return The group name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the group name.
     *
     * @param name The group name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the group description.
     *
     * @return The group description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the group description.
     *
     * @param description The group description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    /**
     *
     * @param userEvents
     */
    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

}
