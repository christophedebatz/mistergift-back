package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserPersistenceService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Inject;
import java.util.Set;


/**
 * The Class AuthenticatedUser emulates an http request scope bean for the
 * authenticated user that is available inside an HttpFilter.
 */
@Component
public class AuthenticatedUser {

    /** The user thread local. */
    private static ThreadLocal<Long> userThreadLocal = new ThreadLocal<Long>();

    /** The user service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /**
     * Gets the user from the current entity manager to keep these entities
     * (user and roles) managed all along - does not add cost anyway.
     *
     * @return the user
     */
    public User getUser() {
        final Long userId = userThreadLocal.get();
        return userId == null ? null : userPersistenceService.findOne(userId);
    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public User.Role getRole() {
        return getUser() == null ? null : getUser().getRole();
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id.
     */
    public static void setUserId(final Long userId) {
        userThreadLocal.set(userId);
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public static Long getUserId() {
        return userThreadLocal.get();
    }

}
