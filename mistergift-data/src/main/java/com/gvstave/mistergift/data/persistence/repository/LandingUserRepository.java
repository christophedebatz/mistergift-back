package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.LandingUser;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link LandingUser}.
 */
@Repository
public interface LandingUserRepository extends EntityRepository<LandingUser, Long> {
}
