package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.persistence.jpa.repository.UserRepositoryJpa;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link User}.
 */
@Service
public class UserPersistenceServiceJpa extends JpaEntityPersistenceService<User, Long> {

    /**
     * Returns the user by its email.
     * @param email The user email.
     * @return The user.
     */
    public User findByEmail(String email) {
        return ((UserRepositoryJpa)getRepository()).findByEmail(email);
    }

}
