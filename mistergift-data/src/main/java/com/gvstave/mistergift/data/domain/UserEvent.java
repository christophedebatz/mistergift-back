package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mysema.query.annotations.QueryInit;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "users_events")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEvent implements BaseEntity<UserEventId> {

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
