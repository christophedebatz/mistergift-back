package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.Event;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Event}.
 */
@Service
public class EventPersistenceService extends EntityPersistenceService<Event, Long> {

}
