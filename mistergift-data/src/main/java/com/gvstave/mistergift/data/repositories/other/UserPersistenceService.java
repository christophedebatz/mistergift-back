package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.User;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link User}.
 */
@Service
public class UserPersistenceService extends EntityPersistenceService<User, Long> {

    /**
     * Returns the user by its email.
     * @param email The user email.
     * @return The user.
     */
    public User findByEmail(String email) {
        return ((UserRepository)getRepository()).findByEmail(email);
    }

}
