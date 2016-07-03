package com.gvstave.mistergift.data.domain;

import com.mysema.query.annotations.QueryInit;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "users_events")
public class UserEvent implements BaseEntity<UserEventId> {

    /** The id. */
    @QueryInit({"event", "user"})
    @EmbeddedId
    private UserEventId id;

    /** The user event type. */
    @Column(name = "can_see_others")
    private boolean canSeeOthers;

    @Column(name = "can_see_mine")
    private boolean canSeeMine;

    @Column(name = "is_admin")
    private boolean isAdmin;

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
    public boolean isCanSeeMine() {
        return canSeeMine;
    }

    /**
     *
     * @param canSeeMine
     */
    public void setCanSeeMine(boolean canSeeMine) {
        this.canSeeMine = canSeeMine;
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

}
