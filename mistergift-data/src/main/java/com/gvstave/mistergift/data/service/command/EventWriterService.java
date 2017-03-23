package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
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

    @Inject
    private EventInvitationPersistenceService eventInvitationPersistenceService;

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

        Event event = eventPersistenceService.findOne(id);
        List<ExternalUserDto> results = new ArrayList<>();

        String from = env.getProperty("mail.from");
        User user = authenticatedUser.getUser();
        Locale locale = user.getLocale();

        // import external users data
        externalUsers.forEach(externalUser -> {
            boolean emailValid = EmailValidator.getInstance().isValid(externalUser.getEmail());
            if (emailValid) {
                String[] recipients = {externalUser.getEmail()};

                User external = new User();
                external.setEmail(externalUser.getEmail());
                external.setLocale(locale);
                external.setFirstName(externalUser.getFirstName());
                external.setLastName(externalUser.getLastName());
                external.setRole(User.Role.ROLE_EXTERNAL);
                external.setCreationDate(new Date());
                external.setModificationDate(new Date());

                EventInvitation invitation = new EventInvitation();
                invitation.setKey(UUID.randomUUID().toString());
                invitation.setType(externalUser.getType());
                invitation.setEvent(event);
                invitation.setAdmin(externalUser.isAdmin());
                invitation.setTargetUser(external);
                invitation.setSenderUser(user);

                eventInvitationPersistenceService.save(invitation);

                Map<String, Object> model = new HashMap<>();
                model.put("senderUser", user);
                model.put("externalUser", external);
                model.put("event", event);

                try {
                    externalUserEmailingService.send(from, recipients, model, locale);
                    results.add(externalUser);
                } catch (MailException e) {
                    LOGGER.error("An error occurred when sending invitation email to {}, error = {}", external.getEmail(), e.getMessage());
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