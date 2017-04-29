package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "user_event_participations",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"event_id", "participant_id"})}
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEventParticipation extends AbstractJpaBaseEntity<Long> {

    /**
     * The user event filter.
     */
    public enum UserEventFilter {

        /** The relation describes that the user is an admin of the event. */
        ADMIN("admin"),

        /** The relation describes that the user has been invited to join the event. */
        INVITATION("invitation"),

        /**
         * The relation describes that the user participates to the event
         * and that all participants can see its gifts.
         */
        CAN_SEE_MINES("can-see-mine"),

        /** The relation describes that the user participates to the event
         * and that he can see the gifts of other participants.
         */
        CAN_SEE_OTHERS("can-see-others");

        /** The filter. */
        String filter;

        /**
         * Constructor.
         *
         * @param filter The filter.
         */
        UserEventFilter(String filter) {
            this.filter = filter;
        }

        /**
         * Returns the filter.
         *
         * @return The filter.
         */
        public String getFilter () {
            return filter;
        }

    }

    @ManyToOne
    @JoinColumn(name = "event_id")
    @JsonBackReference
    private Event event;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;

    /** Whether other event users can see other event users whislist. */
    @Column(name = "can_see_others")
    private boolean canSeeOthers;

    /** Whether other event users can see my whislist. */
    @Column(name = "can_see_mines")
    private boolean canSeeMines;

    /** Whether event user is the event admin. */
    @Column(name = "is_admin")
    private boolean isAdmin;

    /** The user associated invitation */
    @OneToOne
    @JoinColumn(name = "invitation_id", referencedColumnName = "id", nullable = true)
    private EventInvitation invitation;

    /**
     * Hibernate constructor.
     */
    public UserEventParticipation() {
        // nothing
    }

    /**
     * Constructor.
     *
     * @param id
     * @param isAdmin
     */
    public UserEventParticipation(Long id, boolean isAdmin) {
        setId(id);
        this.isAdmin = isAdmin;
    }

    public UserEventParticipation(Event event, User participant, boolean isAdmin) {
        this.event = event;
        this.participant = participant;
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return
     */
    public boolean canSeeOthers() {
        return canSeeOthers;
    }

    /**
     *
     * @param canSeeOthers
     */
    public void setCanSeeOthers(boolean canSeeOthers) {
        this.canSeeOthers = canSeeOthers;
    }

    /**
     *
     * @return
     */
    public boolean canSeeMines() {
        return canSeeMines;
    }

    /**
     *
     * @param canSeeMines
     */
    public void setCanSeeMines(boolean canSeeMines) {
        this.canSeeMines = canSeeMines;
    }

    /**
     *
     * @return
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param admin
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     *
     * @return
     */
    public boolean isInvitation() {
        return invitation == null;
    }

    /**
     * Returns the invitation.
     *
     * @return The invitation.
     */
    public EventInvitation getInvitation () {
        return invitation;
    }

    /**
     * Sets the invitation.
     *
     * @param invitation The invitation.
     */
    public void setInvitation (EventInvitation invitation) {
        this.invitation = invitation;
    }

    /**
     *
     * @return
     */
    public Event getEvent() {
        return event;
    }

    /**
     *
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     *
     * @return
     */
    public User getParticipant() {
        return participant;
    }

    /**
     *
     * @param participant
     */
    public void setParticipant(User participant) {
        this.participant = participant;
    }

    /**
     *
     * @return
     */
    public boolean isCanSeeOthers() {
        return canSeeOthers;
    }

    /**
     *
     * @return
     */
    public boolean isCanSeeMines() {
        return canSeeMines;
    }
}
