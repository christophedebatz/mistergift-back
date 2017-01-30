package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.EventInvitation;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link EventInvitation}.
 */
@Repository
public interface EventInvitationRepository extends EntityRepository<EventInvitation, Long> {
}
