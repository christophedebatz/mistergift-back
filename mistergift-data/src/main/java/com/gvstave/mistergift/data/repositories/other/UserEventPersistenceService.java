package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link UserEvent}.
 */
@Service
public class UserEventPersistenceService extends EntityPersistenceService<UserEvent, UserEventId> {

}
