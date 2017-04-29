package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link UserEventParticipation}.
 */
@Repository
public interface UserEventParticipationRepository extends EntityRepository<UserEventParticipation, Long> {
}
