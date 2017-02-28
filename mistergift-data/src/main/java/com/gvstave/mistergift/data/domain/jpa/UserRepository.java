package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import com.gvstave.mistergift.data.domain.jpa.User;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link User}.
 */
@Repository
public interface UserRepository extends EntityRepository<User, Long> {

    /**
     * Returns the user who is associated with the given email.
     *
     * @param email The user email.
     * @return The user if it exists.
     */
    User findByEmail(String email);

}