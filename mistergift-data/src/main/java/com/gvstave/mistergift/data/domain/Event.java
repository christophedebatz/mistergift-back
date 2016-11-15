package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "events")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event extends AbstractTimestampableJpaEntity<Long> {

    /**
     * Describes the event status.
     */
    public enum EventStatus {

        /** When an event has been removed. */
        REMOVED("removed"),

        /** When an event has been cancelled. */
        CANCELLED("cancelled"),

        /** When an event is unpublished. */
        UNPUBLISHED("unpublished"),

        /** When an event is currently available. */
        PUBLISHED("published");

        /** The status. */
        String status;

        /**
         * Constructor.
         *
         * @param status The event status.
         */
        EventStatus(String status) {
            this.status = status;
        }

        /**
         * Returns the event status.
         *
         * @return The event status.
         */
        public String getStatus() {
            return status;
        }

        /**
         * Returns {@link EventStatus} from its representative String.
         *
         * @param str The string.
         * @return The event status.
         */
        public static EventStatus fromString(String str) throws InvalidFieldValueException {
            return Arrays.asList(EventStatus.values()).stream()
                .filter(status -> status.getStatus().equalsIgnoreCase(str)).findFirst()
                .orElseThrow(() -> new InvalidFieldValueException("fromString::str"));
        }

    }

    /** The event name. */
    @Column(name = "name", length = 75, unique = true, nullable = false)
    private String name;

    /** The event status. */
    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EventStatus status = EventStatus.UNPUBLISHED;

    /** The event description. */
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    /** The event start date. */
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    /** The event end date. */
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    /** The event address. */
    @Column(name = "address", length = 255)
    private String address;

    /** The event cover picture id. */
    @OneToOne
    @JoinColumn(name = "cover_id", nullable = true)
    private FileMetadata cover;

    /** The group members. */
    @JsonProperty("participants")
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "event_participants",
        joinColumns = { @JoinColumn(name = "event_id", nullable = false, updatable = false) }
    )
    private List<UserEvent> participants;

    /**
     * Constructor.
     */
    public Event() {
        this.participants = new ArrayList<>();
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
     *
     * @return
     */
    public EventStatus getStatus () {
        return status;
    }

    public void setStatus (EventStatus status) {
        this.status = status;
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

    public Date getStartDate () {
        return startDate;
    }

    public void setStartDate (Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate () {
        return endDate;
    }

    public void setEndDate (Date endDate) {
        this.endDate = endDate;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public FileMetadata getCover () {
        return cover;
    }

    public void setCover (FileMetadata cover) {
        this.cover = cover;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public List<UserEvent> getParticipants () {
        return participants;
    }

    /**
     *
     * @param participants
     */
    public void setParticipants (List<UserEvent> participants) {
        this.participants = participants;
    }

    /**
     *
     */
    public void cancel() {
        status = EventStatus.CANCELLED;
    }

    /**
     *
     */
    public void remove() {
        status = EventStatus.REMOVED;
    }

    /**
     *
     */
    public void publish() {
        status = EventStatus.PUBLISHED;
    }

    /**
     *
     */
    public void unpublish() {
        status = EventStatus.UNPUBLISHED;
    }

}
