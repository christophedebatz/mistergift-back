package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.User;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link User}.
 */
@Service
public class UserPersistenceService extends BaseEntityPersistenceService<User, Long> {

    /**
     * Constructor.
     */
    public UserPersistenceService() {
        super(User.class);
    }

    /**
     * Returns the user by its email.
     * @param email The user email.
     * @return The user.
     */
    public User findByEmail(String email) {
        return null;
    }

}
