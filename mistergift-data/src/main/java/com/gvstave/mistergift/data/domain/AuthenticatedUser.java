package com.gvstave.mistergift.data.domain;

import com.gvstave.mistergift.data.domain.jpa.User;
import org.springframework.web.context.annotation.RequestScope;

/**
 * The authenticated user data.
 */
@RequestScope
public class AuthenticatedUser {

    /** The user. */
    private User user;

    /** The user id. */
    private Long userId;

    /**
     * Constructor.
     *
     * @param user   The user.
     * @param userId The user id.
     */
    public AuthenticatedUser(User user, Long userId) {
        this.user = user;
        this.userId = userId;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
