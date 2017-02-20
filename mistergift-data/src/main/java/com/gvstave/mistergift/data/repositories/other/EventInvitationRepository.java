package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.EventInvitation;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link EventInvitation}.
 */
@Repository
public interface EventInvitationRepository extends EntityRepository<EventInvitation, Long> {
}
