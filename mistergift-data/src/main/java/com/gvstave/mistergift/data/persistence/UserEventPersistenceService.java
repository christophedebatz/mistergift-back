package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.UserEvent;
import com.gvstave.mistergift.data.domain.UserEventId;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link UserEvent}.
 */
@Service
public class UserEventPersistenceService extends EntityPersistenceService<UserEvent, UserEventId> {

}
