package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.Event;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Event}.
 */
@Service
public class EventPersistenceServiceJpa extends JpaEntityPersistenceService<Event, Long> {

}
