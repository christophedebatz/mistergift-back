package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

/**
 * The event service.
 */
@Service
public class EventService {

    /** The event persistence service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /**
     * Returns an event by its id, or null if it does not exist.
     *
     * @param eventId The event id.
     * @return The event.
     */
    public Event getById(Long eventId) {
        Objects.requireNonNull(eventId);
        return eventPersistenceService.findOne(eventId);
    }

}