package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.EventInvitation;
import org.springframework.stereotype.Service;

/**
 * Repository for {@link EventInvitation}.
 */
@Service
public class EventInvitationPersistenceServiceJpa extends JpaEntityPersistenceService<EventInvitation, Long> {

}
