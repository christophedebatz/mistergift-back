package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.Event;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Event}.
 */
@Repository
public interface EventRepository extends EntityRepository<Event, Long> {
}
