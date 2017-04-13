package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import com.gvstave.mistergift.data.domain.jpa.EventPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserEventPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserPersistenceService;
import com.gvstave.mistergift.data.utils.Streams;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The user event service.
 */
@Service
public class UserEventService {

    /** The user event repositories service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The user repositories service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The event repositories service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /**
     * Retrieves the user events.
     *
     * @param user The current user.
     * @param filters The event filter.
     * @param pageable The pageable.
     * @return The user events.
     */
    public Map<String, List<Event>> getUserEvents(User user, String filters, Pageable pageable) {
        final Map<String, List<Event>> events = new HashMap<>();
        final List<String> eventStatus;

        if (filters != null) {
            eventStatus = Arrays.asList(filters.replaceAll("\\s+","").split(","));
        } else {
            eventStatus = Arrays.asList(
                    UserEvent.UserEvenFilter.INVITATION.getFilter(),
                    UserEvent.UserEvenFilter.ADMIN.getFilter(),
                    Event.EventStatus.CANCELLED.getStatus(),
                    Event.EventStatus.UNPUBLISHED.getStatus(),
                    Event.EventStatus.PUBLISHED.getStatus()
            );
        }

        // user-event relation types
        // pending event / invitation from other participants
        String pendingFlag = UserEvent.UserEvenFilter.INVITATION.getFilter();
        if (eventStatus.contains(pendingFlag)) {
            List<Event> inviteEvents = getUserInvitationEvents(user, pageable);
            events.put(pendingFlag, inviteEvents);
        }

        // event where i'm admin
        String adminFlag = UserEvent.UserEvenFilter.ADMIN.getFilter();
        if (eventStatus.contains(adminFlag)) {
            events.put(adminFlag, getUserAdminEvents(user, pageable));
        }

        // event status type
        // cancelled events
        String cancelledFlag = Event.EventStatus.CANCELLED.getStatus();
        if (eventStatus.contains(cancelledFlag)) {
            List<Event> cancelled = getUserEventsByStatus(user, Event.EventStatus.CANCELLED, pageable);
            events.put(cancelledFlag, cancelled);
        }

        // my unpublished events
        String unpublishedFlag = Event.EventStatus.UNPUBLISHED.getStatus();
        if (eventStatus.contains(unpublishedFlag)) {
            List<Event> unpublished = getUserEventsByStatus(user, Event.EventStatus.UNPUBLISHED, pageable);
            events.put(unpublishedFlag, unpublished);
        }

        // the published events
        String publishedFlag = Event.EventStatus.PUBLISHED.getStatus();
        if (eventStatus.contains(publishedFlag)) {
            List<Event> published = getUserEventsByStatus(user, Event.EventStatus.PUBLISHED, pageable);
            events.put(publishedFlag, published);
        }

        return events;
    }

    /**
     * Returns the event according to their {@link Event.EventStatus}.
     * Note: the unpublished events can be see only by event admins.
     *
     * @param user The user.
     * @param status The event status.
     * @param pageable The pageable if necessary.
     * @return The events.
     */
    @Transactional(readOnly = true)
    private List<Event> getUserEventsByStatus(User user, Event.EventStatus status, Pageable pageable) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(status);

        BooleanExpression bUser = QEvent.event.participants.any().id.user.eq(user);

        // if unpublished events requested, only admin user can see them
        if (Event.EventStatus.UNPUBLISHED == status) {
            bUser = bUser.and(QEvent.event.participants.any().isAdmin.isTrue());
        }

        BooleanExpression bTotal = bUser.and(QEvent.event.status.eq(status));

        if (pageable != null) {
            return eventPersistenceService.findAll(bTotal, pageable).getContent();
        }

        return Streams.of(eventPersistenceService.findAll())
            .collect(Collectors.toList());
    }

    /**
     * Returns all the user event invitations.
     *
     * @param user     The user.
     * @param pageable The pageable.
     * @return The user events.
     */
    @Transactional(readOnly = true)
    private List<Event> getUserInvitationEvents(User user, Pageable pageable) {
        Objects.requireNonNull(user);

        QUserEvent anyEvent = QEvent.event.participants.any();
        Predicate predicate = anyEvent.id.user.eq(user).and(anyEvent.invitation.isNotNull());

        if (pageable != null) {
            return eventPersistenceService.findAll(predicate, pageable).getContent();
        }

        return eventPersistenceService.streamAll(predicate).collect(Collectors.toList());
    }

    /**
     * Returns the users that is invited to join the given event.
     *
     * @param event    The event.
     * @param pageable The pageable.
     * @return The pending invited user to the event.
     */
    @Transactional(readOnly = true)
    public Page<User> getEventPendingUsers(Event event, Pageable pageable) {
        Objects.requireNonNull(event);

        Predicate predicate = QUserEvent.userEvent.id.event.eq(event)
            .and(QUserEvent.userEvent.invitation.isNotNull());

        Page<UserEvent> userEvent;
        if (pageable != null) {
            userEvent = userEventPersistenceService.findAll(predicate, pageable);
        } else {
            List<UserEvent> userEvents = Streams.of(userEventPersistenceService.findAll(predicate))
                .collect(Collectors.toList());
            userEvent = new PageImpl<>(userEvents);
        }

        List<User> pendingUsers = Streams.of(userEvent)
            .map(UserEvent::getId)
            .map(UserEventId::getUser)
            .collect(Collectors.toList());

        return new PageImpl<>(pendingUsers);
    }

    /**
     * Returns the events that a user is the administrator.
     *
     * @param user The administrator.
     * @return The list of administrated events.
     */
    @Transactional(readOnly = true)
    private List<Event> getUserAdminEvents(User user, Pageable pageable) {
        Objects.requireNonNull(user);

        QUserEvent anyEvent = QEvent.event.participants.any();
        Predicate predicate = anyEvent.id.user.eq(user).and(anyEvent.isAdmin.isTrue());

        if (pageable != null) {
            return eventPersistenceService.findAll(predicate, pageable).getContent();
        }

        return Streams.of(eventPersistenceService.findAll(predicate))
            .collect(Collectors.toList());
    }

    /**
     *
     * @param event
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<User> getEventMembers (Event event, Pageable pageable) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(pageable);

        QUserEvent qUserEvent = QUserEvent.userEvent;
        Predicate predicate = qUserEvent.id.event.eq(event).and(qUserEvent.invitation.isNull());

        return userPersistenceService.findAll(predicate, pageable);
    }

    /**
     * Returns the event administrators.
     *
     * @param event The event.
     * @param pageable The pageable.
     * @return The event administrators.
     */
    @Transactional(readOnly = true)
    public Page<User> getEventAdmins(Event event, Pageable pageable) {
        Objects.requireNonNull(event);
        Objects.requireNonNull(pageable);

        QUserEvent qUserEvent = QUser.user.userEvents.any();
        Predicate predicate = qUserEvent.id.event.eq(event).and(qUserEvent.isAdmin.isTrue());

        return userPersistenceService.findAll(predicate, pageable);
    }

    /**
     * Returns if the given user is an api of the given event id.
     *
     * @param user The user.
     * @param eventId The event id.
     * @return whether the user is part of the event admins.
     */
    @Transactional(readOnly = true)
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
