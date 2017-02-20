package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.repositories.other.EventPersistenceService;
import com.gvstave.mistergift.data.repositories.other.UserEventPersistenceService;
import com.gvstave.mistergift.data.service.query.UserEventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

/**
 * The service that can write events.
 */
@Service
public class EventWriterService {

    /** The user event repositories service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The event repositories service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /** The user event service. */
    @Inject
    private UserEventService userEventService;

    /**
     * Creates new event and add it to the given user.
     *
     * @param event The event to create.
     * @param user  The event user.
     * @return The created event.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @Transactional(readOnly = false)
    public Event createEvent(Event event, User user) throws UnauthorizedOperationException, InvalidFieldValueException {
        Objects.requireNonNull(event);
        Objects.requireNonNull(user);

        ensureUserCanUpdateEvent(event, false);

        Event savedEvent = eventPersistenceService.save(event); // useless?
        UserEventId userEventId = new UserEventId(savedEvent, user);
        UserEvent userEvent = new UserEvent(userEventId, true);
        userEvent = userEventPersistenceService.save(userEvent);

        return userEvent.getId().getEvent();
    }

    /**
     * Removes an event.
     *
     * @param eventId The event id to remove.
     * @param user    The user.
     * @throws UnauthorizedOperationException If operation is not permitted for the user.
     */
    @Transactional(readOnly = false)
    public void deleteEvent(Long eventId, User user) throws UnauthorizedOperationException {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(user);

        if (!userEventService.isUserEventAdmin(user, eventId)) {
            throw new UnauthorizedOperationException("remove event");
        }
        eventPersistenceService.delete(eventId);
    }

    /**
     * Updates the status of an event.
     *
     * @param eventId The event id.
     * @param status  The event new status as a string.
     * @param user    The user.
     * @throws UnauthorizedOperationException If operation is not permitted for the user.
     * @throws InvalidFieldValueException If the status is not recognized.
     */
    @Transactional(readOnly = false)
    public void updateStatus(Long eventId, String status, User user) throws UnauthorizedOperationException,
        InvalidFieldValueException {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(status);
        Objects.requireNonNull(user);

        if (!userEventService.isUserEventAdmin(user, eventId)) {
            throw new UnauthorizedOperationException("update event status");
        }
        Optional<Event> event = Optional.of(eventPersistenceService.findOne(eventId));

        // not using ifPresent because non
        if (event.isPresent()) {
            event.get().setStatus(Event.EventStatus.fromString(status));
            eventPersistenceService.save(event.get());
        } else {
            throw new EntityNotFoundException("Unable to retrieve event with id=" + eventId);
        }
    }

    /**
     *
     * @param event
     * @param user
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @Transactional(readOnly = false)
    public Event updateEvent(Event event, User user) throws UnauthorizedOperationException, InvalidFieldValueException {
        Objects.requireNonNull(event);
        Objects.requireNonNull(user);
        ensureUserCanUpdateEvent(event, true);

        if (!userEventService.isUserEventAdmin(user, event.getId())) {
            throw new UnauthorizedOperationException("update event status");
        }

        return eventPersistenceService.save(event);
    }

    /**
     * Ensure that given event is correctly hydrated.
     *
     * @param event    The event.
     * @param isUpdate Whether the action implies event-update.
     * @throws UnauthorizedOperationException if operation is not permitted for this event.
     * @throws InvalidFieldValueException     if a field value is invalid.
     */
    private void ensureUserCanUpdateEvent (Event event, boolean isUpdate) throws UnauthorizedOperationException, InvalidFieldValueException {
        if (isUpdate && (event.getId() == null || event.getId() <= 0)) {
            throw new InvalidFieldValueException("id");
        }

        if (event.getName() == null || event.getName().isEmpty()) {
            throw new InvalidFieldValueException("name");
        }

    }


}