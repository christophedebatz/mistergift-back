package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryInit;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "users_events")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEvent implements BaseEntity<UserEventId> {

    /**
     * The user event filter.
     */
    public enum UserEvenFilter {

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
        UserEvenFilter(String filter) {
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

    /** The id. */
    @QueryInit({"event", "user"})
    @EmbeddedId
    private UserEventId id;

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
    public UserEvent() {
        // nothing
    }

    /**
     * Constructor.
     *
     * @param id
     * @param isAdmin
     */
    public UserEvent(UserEventId id, boolean isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return
     */
    @Override
    public UserEventId getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    @Override
    public void setId(UserEventId id) {
        this.id = id;
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

}
