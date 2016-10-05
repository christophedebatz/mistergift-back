package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mysema.query.annotations.QueryInit;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "mistergift", name = "users_events")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEvent implements BaseEntity<UserEventId> {

    /** The id. */
    @QueryInit({"event", "user"})
    @EmbeddedId
    private UserEventId id;

    /** The user event type. */
    @Column(name = "can_see_others")
    private boolean canSeeOthers;

    @Column(name = "can_see_mines")
    private boolean canSeeMines;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "is_invitation")
    private boolean isInvitation;

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
    public boolean isCanSeeOthers() {
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
    public boolean isCanSeeMines() {
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
        return isInvitation;
    }

    /**
     *
     * @param invitation
     */
    public void setInvitation(boolean invitation) {
        isInvitation = invitation;
    }

}
