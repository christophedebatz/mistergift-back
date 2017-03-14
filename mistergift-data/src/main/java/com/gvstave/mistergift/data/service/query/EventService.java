package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The event service.
 */
@Service
public class EventService {

    /** The event repositories service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    @Inject
    private UserEventPersistenceService userEventPersistenceService;

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

    /**
     *
     * @param sourceUserId The source user id.
     * @param targetUser The target user.
     * @return
     */
    public List<UserEvent> retrieveCommonUsersEvents(Long sourceUserId, User targetUser) {
        BooleanExpression query = QUserEvent.userEvent.id.user.id.eq(sourceUserId).and(QUserEvent.userEvent.id.user.eq(targetUser));
        return userEventPersistenceService.streamAll(query).collect(Collectors.toList());
    }

}
