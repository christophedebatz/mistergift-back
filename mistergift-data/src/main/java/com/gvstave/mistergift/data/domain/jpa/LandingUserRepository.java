package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import com.gvstave.mistergift.data.domain.jpa.LandingUser;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link LandingUser}.
 */
@Repository
public interface LandingUserRepository extends EntityRepository<LandingUser, Long> {
}
