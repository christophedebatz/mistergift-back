package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Participation}.
 */
@Repository
public interface ParticipationRepository extends EntityRepository<Participation, Long> {
}
