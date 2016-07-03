package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.*;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 * The event service.
 */
@Service
public class EventService {

    /** The event persistence service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    @Inject
    private EventPersistenceService eventPersistenceService;

    /**
     * Creates new event and add it to the given user.
     *
     * @param event The event to create.
     * @param user  The event user.
     * @return The created event.
     */
    @Transactional
    public Event createEvent(Event event, User user) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(user);

        UserEventId userEventId = new UserEventId(null, user);
        UserEvent userEvent = new UserEvent(userEventId, true);
        userEvent = userEventPersistenceService.save(userEvent);

        return userEvent.getId().getEvent();
    }

    /**
     * Returns the user events.
     *
     * @param user The user.
     * @return The user events.
     */
    public Page<Event> getUserEvents(User user, Pageable pageable) {
        Objects.requireNonNull(user);
        return eventPersistenceService.findAll(QEvent.event.userEvents.any().id.user.eq(user), pageable);
    }

    /**
     * Returns if the given user is an api of the given event id.
     *
     * @param user The user.
     * @param eventId The event id.
     * @return whether the user is part of the event admins.
     */
    public boolean isUserEventAdmin(User user, Long eventId) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(eventId);

        QUserEvent qEvent = QUserEvent.userEvent;
        BooleanExpression qEventExp = qEvent.id.event.id.eq(eventId)
                .and(qEvent.id.user.eq(user))
                .and(qEvent.isAdmin.isTrue());

        return Optional.ofNullable(userEventPersistenceService.findOne(qEventExp)).isPresent();
    }

}
