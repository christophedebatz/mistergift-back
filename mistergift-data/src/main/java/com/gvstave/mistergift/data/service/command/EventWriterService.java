package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.AuthenticatedUser;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.domain.jpa.EventPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserEventPersistenceService;
import com.gvstave.mistergift.data.service.dto.ExternalUserDto;
import com.gvstave.mistergift.data.service.query.UserEventService;
import com.gvstave.mistergift.service.mailing.ExternalUserEmailingService;
import com.gvstave.mistergift.service.mailing.exception.MailException;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.*;

/**
 * The service that can write events.
 */
@Service
public class EventWriterService {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(EventWriterService.class);

    /** The environment. */
    @Inject
    private Environment env;

    /** The user event repositories service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The event repositories service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /** The user event service. */
    @Inject
    private UserEventService userEventService;

    /** The external user emailing service. */
    @Inject
    private ExternalUserEmailingService externalUserEmailingService;

    /** The authenticated user. */
    @Inject
    private AuthenticatedUser authenticatedUser;

    /**
     * Creates new event and add it to the given user.
     *
     * @param event The event to create.
     * @param user  The event user.
     * @return The created event.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @Transactional
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
    @Transactional
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
     * @throws InvalidFieldValueException     If the status is not recognized.
     */
    @Transactional
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
     * @param event
     * @param user
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @Transactional
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
     * Invites some external user to an event.
     *
     * @param id            The event id.
     * @param externalUsers The external users dtos.
     * @return The external users dto list.
     */
    @Transactional
    public List<ExternalUserDto> inviteExternalUsers(Long id, List<ExternalUserDto> externalUsers) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(externalUsers);

        List<ExternalUserDto> results = new ArrayList<>();

        // import external users data
        externalUsers.forEach(user -> {
            boolean emailValid = EmailValidator.getInstance().isValid(user.getEmail());
            if (emailValid) {
                Locale locale = authenticatedUser.getUser().getLocale();
                String[] recipients = {user.getEmail()};
                String from = env.getProperty("mail.from");

                User external = new User();
                external.setEmail(user.getEmail());
                external.setLocale(locale);
                external.setName(user.getName());
                external.setRole(User.Role.ROLE_EXTERNAL);
                external.setCreationDate(new Date());
                external.setModificationDate(new Date());

                Map<String, Object> model = new HashMap<>();
                model.put("sender-name", user.getSender().getName());
                model.put("name", user.getName());
                model.put("email", user.getEmail());

                try {
                    externalUserEmailingService.send(from, recipients, model, locale);
                    results.add(user);
                } catch (MailException e) {
                    LOGGER.error("An error occurred when sending invitation email to {}, error = {}", user.getEmail(), e.getMessage());
                }
            }
        });

        return results;
    }

    /**
     * Ensure that given event is correctly hydrated.
     *
     * @param event    The event.
     * @param isUpdate Whether the action implies event-update.
     * @throws UnauthorizedOperationException if operation is not permitted for this event.
     * @throws InvalidFieldValueException     if a field value is invalid.
     */
    private void ensureUserCanUpdateEvent(Event event, boolean isUpdate) throws UnauthorizedOperationException, InvalidFieldValueException {
        if (isUpdate && (event.getId() == null || event.getId() <= 0)) {
            throw new InvalidFieldValueException("id");
        }

        if (event.getName() == null || event.getName().isEmpty()) {
            throw new InvalidFieldValueException("name");
        }

    }

}