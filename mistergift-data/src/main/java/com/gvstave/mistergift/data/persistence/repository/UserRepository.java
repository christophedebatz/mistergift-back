package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link User}.
 */
@Repository
public interface UserRepository extends BaseEntityRepository<User, Long> {

    /**
     * Returns the user who is associated with the given email.
     *
     * @param email The user email.
     * @return The user if it exists.
     */
    User findByEmail(String email);

}