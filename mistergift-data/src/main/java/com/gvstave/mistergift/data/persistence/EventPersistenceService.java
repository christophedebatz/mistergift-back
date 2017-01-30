package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Event;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Event}.
 */
@Service
public class EventPersistenceService extends EntityPersistenceService<Event, Long> {

}
