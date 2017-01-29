package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Event;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Event}.
 */
@Repository
public interface EventRepository extends BaseEntityRepository<Event, Long> {
}
