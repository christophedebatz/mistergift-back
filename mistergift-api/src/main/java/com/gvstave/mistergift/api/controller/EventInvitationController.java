package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.*;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.persistence.EventInvitationPersistenceService;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EventInvitationController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(EventInvitationController.class);

    /** The event invitation persistence service. */
    @Inject
    private EventInvitationPersistenceService eventInvitationPersistenceService;

    /** The event persistence service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /** The user event persistence service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /**
     * Invites user to join an event.
	 *
     * @param event The event (only id is required).
     * @param userId The user id.
     * @throws InvalidFieldValueException If event have no id.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST, path = "/users/{id}/invitations")
    public @ResponseBody ResponseEntity<EventInvitation> invite(@RequestBody Event event, @PathVariable(value = "id") Long userId) throws
        InvalidFieldValueException, UnauthorizedOperationException {
        Objects.requireNonNull(event);
        Objects.requireNonNull(userId);

        if (event.getId() == null) {
            throw new InvalidFieldValueException("event id");
        }

        LOGGER.debug("Invite user:id={} to event:id={}", userId, event.getId());
        Optional<User> targetUser = Optional.ofNullable(userPersistenceService.findOne(userId));
        Optional<Event> targetEvent = Optional.ofNullable(eventPersistenceService.findOne(QEvent.event.eq(event)));

        if (targetEvent.isPresent() && targetUser.isPresent()) {
            boolean userCanInvit = targetEvent.get().getParticipants().stream()
                .filter(UserEvent::isAdmin)
                .map(UserEvent::getId)
                .map(UserEventId::getUser)
                .anyMatch(user -> getUser().equals(user));

            if (userCanInvit) {
                EventInvitation invitation = new EventInvitation();
                invitation.setSenderUser(getUser());
                invitation.setTargetUser(targetUser.get());
                invitation.setEvent(targetEvent.get());
                invitation = eventInvitationPersistenceService.save(invitation);

                UserEvent userEvent = new UserEvent();
                userEvent.setInvitation(invitation);
                userEvent.setId(new UserEventId(targetEvent.get(), targetUser.get()));
                userEventPersistenceService.save(userEvent);

                return ResponseEntity.ok(invitation);
            } else {
                throw new UnauthorizedOperationException("invite user as a non-admin user");
            }
        } else {
            throw new EntityNotFoundException("Target user or target event has been not found");
        }
    }

    /**
     *
     * @param eventId
     * @throws InvalidFieldValueException
     * @throws UnauthorizedOperationException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST, path = "/me/events/{eventId}")
    public void accept(@PathVariable(value = "eventId") Long eventId)
        throws InvalidFieldValueException, UnauthorizedOperationException {
        Objects.requireNonNull(eventId);
        LOGGER.debug("Accept invitation for event:id={}", eventId);

        QUserEvent qUserEvent = QUserEvent.userEvent;
        Optional.ofNullable(
            userEventPersistenceService.findOne(
                qUserEvent.id.event.id.eq(eventId)
                    .and(qUserEvent.id.user.eq(getUser()))
                    .and(qUserEvent.invitation.isNotNull())
            )
        )
        .ifPresent(userEvent -> {
            EventInvitation invitation = userEvent.getInvitation();
            userEvent.setAdmin(invitation.isAdmin());
            userEvent.setInvitation(null);

            switch (invitation.getType()) {

                case TARGET:
                    userEvent.setCanSeeMines(true);
                    userEvent.setCanSeeOthers(false);
                    break;

                case PARTICIPANT:
                    userEvent.setCanSeeMines(false);
                    userEvent.setCanSeeOthers(true);
                    break;

                default:
                    userEvent.setCanSeeMines(true);
                    userEvent.setCanSeeOthers(true);
                    break;
            }

            eventInvitationPersistenceService.delete(invitation);
            userEventPersistenceService.save(userEvent);
        });

    }

    /**
     *
     * @param invitationId
     * @throws InvalidFieldValueException
     * @throws UnauthorizedOperationException
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, path = "/me/invitations/{invitationId}")
    public void refuse(@PathVariable(value = "invitationId") Long invitationId) throws InvalidFieldValueException, UnauthorizedOperationException {
        Objects.requireNonNull(invitationId);
        LOGGER.debug("Refuse invitation for invitation:id={}", invitationId);

        Optional.ofNullable(eventInvitationPersistenceService.findOne(invitationId))
            .filter(filter -> filter.getTargetUser().equals(getUser()))
            .ifPresent(invitation -> {
                userEventPersistenceService.delete(invitation.getId());
                QUserEvent qUserEvent = QUserEvent.userEvent;
                Optional.ofNullable(
                    userEventPersistenceService.findOne(
                        qUserEvent.id.user.eq(getUser())
                            .and(qUserEvent.id.event.eq(invitation.getEvent())
                                .and(qUserEvent.invitation.isNotNull()))))
                .ifPresent(userEventPersistenceService::delete);
            }
        );
    }

    /**
     *
     * @param userId
     * @param invitationId
     * @throws InvalidFieldValueException
     * @throws UnauthorizedOperationException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/users/{userId}/invitations/{invitationId}")
    public void cancel(@PathVariable(value = "userId") Long userId,
                       @PathVariable(value = "invitationId") Long invitationId) throws InvalidFieldValueException, UnauthorizedOperationException {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(invitationId);
        LOGGER.debug("Cancel invitation from user:id={} for invitation:id={}", userId, invitationId);

        QUserEvent qUserEvent = QUserEvent.userEvent;
        Optional.ofNullable(
            userEventPersistenceService.findOne(
                qUserEvent.invitation.isNotNull()
                    .and(qUserEvent.invitation.id.eq(invitationId)))
        )
        .filter(filter -> filter.getInvitation().getSenderUser().equals(getUser()))
        .ifPresent(userEvent -> {
            eventInvitationPersistenceService.delete(userEvent.getInvitation());
            userEventPersistenceService.delete(userEvent);
        });
    }
}
