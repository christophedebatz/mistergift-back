package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link UserEvent}.
 */
@Service
public class UserEventPersistenceServiceJpa extends JpaEntityPersistenceService<UserEvent, UserEventId> {

}
