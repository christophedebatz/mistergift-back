package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.EventInvitation;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link EventInvitation}.
 */
@Repository
public interface EventInvitationRepositoryJpa extends JpaEntityRepository<EventInvitation, Long> {
}
