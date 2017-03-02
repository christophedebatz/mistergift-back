package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.UUID;

/**
 * .
 */
@Entity
@Table(schema = "mistergift", name = "event_invitations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventInvitation extends AbstractTimestampableJpaBaseEntity<Long> {

    /**
     * Describes the invitation.
     */
    public enum EventInvitationType {

        /** Whether the invited user will be the event target. */
        TARGET("target"),

        /** Whether the invited user will be a participant. */
        PARTICIPANT("participant"),

        /** Whether the invited user will be both a target and a participant. */
        MIXED("mixed");

        /** The target user role. */
        String role;

        /**
         * Constructor.
         *
         * @param role The invited user future role.
         */
        EventInvitationType (String role) {
            this.role = role;
        }

        /**
         * Returns the invited user future role.
         *
         * @return The user role.
         */
        public String getRole () {
            return role;
        }

    }

    /** The identifier of the invitation. */
    @Column(unique = true, nullable = false)
    private String key;

    /** The user who sends the event invitation. */
    @OneToOne
    private User senderUser;

    /** The user who is invited to join the event. */
    @OneToOne
    private User targetUser;

    /** The event. */
    @OneToOne
    private Event event;

    /** Wether the target user will be an admin of the event. */
    @Column(name = "is_admin")
    private boolean admin;

    /** The type of invitation. */
    @Column
    @Enumerated(EnumType.ORDINAL)
    private EventInvitationType type;

    /**
     * Constructor.
     */
    public EventInvitation () {
        this.key = UUID.fromString(getId().toString()).toString().replace("-", "");
    }

    /**
     * Returns the event invitation key.
     *
     * @return The invitation key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the event invitation key.
     *
     * @param key The invitation key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns the user who sends the invitation.
     *
     * @return The sender.
     */
    public User getSenderUser () {
        return senderUser;
    }

    /**
     * Sets the user who sends the invitation.
     *
     * @param senderUser The sender.
     */
    public void setSenderUser (User senderUser) {
        this.senderUser = senderUser;
    }

    /**
     * Returns the user who receives the invitation.
     *
     * @return The target.
     */
    public User getTargetUser () {
        return targetUser;
    }

    /**
     * Sets the user who receives the invitation.
     *
     * @param targetUser The target.
     */
    public void setTargetUser (User targetUser) {
        this.targetUser = targetUser;
    }

    /**
     * Returns the invitation event.
     *
     * @return The event.
     */
    public Event getEvent () {
        return event;
    }

    /**
     * Sets the invitation event.
     *
     * @param event The event.
     */
    public void setEvent (Event event) {
        this.event = event;
    }

    /**
     * Returns whether the target user will be an admin.
     *
     * @return If user is admin.
     */
    public boolean isAdmin () {
        return admin;
    }

    /**
     * Sets whether the target user will be an admin.
     *
     * @param admin If user is admin.
     */
    public void setAdmin (boolean admin) {
        this.admin = admin;
    }

    /**
     * Returns the type of invitation.
     *
     * @return The type.
     */
    public EventInvitationType getType () {
        return type;
    }

    /**
     * Sets the type of invitation.
     *
     * @param type The type.
     */
    public void setType (EventInvitationType type) {
        this.type = type;
    }

}
