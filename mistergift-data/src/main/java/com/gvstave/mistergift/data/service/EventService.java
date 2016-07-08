package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.*;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.utils.Streams;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The event service.
 */
@Service
public class EventService {

    /** The user event persistence service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The event persistence service. */
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
    public List<Event> getUserInvitationEvents(User user) {
        Objects.requireNonNull(user);

        QUserEvent anyEvent = QEvent.event.userEvents.any();
        Predicate predicate = anyEvent.id.user.eq(user).and(anyEvent.isInvitation.isTrue());

        return Streams.of(eventPersistenceService.findAll(predicate))
                .collect(Collectors.toList());
    }

    /**
     * Returns the event pending invited users.
     *
     * @param event The event.
     * @return The pending invited user to the event.
     */
    public List<User> getEventPendingUsers(Event event) {
        Objects.requireNonNull(event);

        Predicate predicate = QUserEvent.userEvent.id.event.eq(event)
                .and(QUserEvent.userEvent.isInvitation.isTrue());

        return Streams.of(userEventPersistenceService.findAll(predicate))
                .map(UserEvent::getId)
                .map(UserEventId::getUser)
                .collect(Collectors.toList());
    }

    /**
     * Returns the events that a user is the administrator.
     *
     * @param user The administrator.
     * @return The list of administrated events.
     */
    public List<Event> getUserAdminEvents(User user) {
        Objects.requireNonNull(user);

        QUserEvent anyEvent = QEvent.event.userEvents.any();
        Predicate predicate = anyEvent.id.user.eq(user).and(anyEvent.isAdmin.isTrue());

        return Streams.of(eventPersistenceService.findAll(predicate))
                .collect(Collectors.toList());
    }

    /**
     * Returns the event administrators.
     *
     * @param event The event.
     * @param pageable The pageable.
     * @return The event administrators.
     */
    public Page<User> getEventAdmins(Event event, Pageable pageable) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(pageable);

        QUserEvent qUserEvent = QUser.user.userEvents.any();
        Predicate predicate = qUserEvent.id.event.eq(event).and(qUserEvent.isAdmin.isTrue());

        return userPersistenceService.findAll(predicate, pageable);
    }

    /**
     * Returns the event users.
     *
     * @param event The event.
     * @param pageable The pageable.
     * @return The event users.
     */
    public Page<User> getEventUsers(Event event, Pageable pageable) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(pageable);

        QUserEvent qUserEvent = QUserEvent.userEvent;
        Predicate predicate = qUserEvent.id.event.eq(event).and(qUserEvent.isInvitation.isFalse());

        return userPersistenceService.findAll(predicate, pageable);
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
