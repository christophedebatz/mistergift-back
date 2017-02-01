package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link User}.
 */
@Repository
public interface UserRepositoryJpa extends JpaEntityRepository<User, Long> {

    /**
     * Returns the user who is associated with the given email.
     *
     * @param email The user email.
     * @return The user if it exists.
     */
    User findByEmail(String email);

}