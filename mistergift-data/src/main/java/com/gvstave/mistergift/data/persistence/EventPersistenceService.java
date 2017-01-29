package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Event;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Event}.
 */
@Service
public class EventPersistenceService extends BaseEntityPersistenceService<Event, Long> {

    /**
     * The default constructor for QueryDsl support.
     */
    protected EventPersistenceService() {
        super(Event.class);
    }

}
