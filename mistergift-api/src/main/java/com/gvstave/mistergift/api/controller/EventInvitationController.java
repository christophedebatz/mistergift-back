package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.domain.EventInvitation;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.service.command.EventInvitationWriterService;
import com.gvstave.mistergift.data.service.query.EventInvitationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;

@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EventInvitationController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(EventInvitationController.class);

    /** The event invitation writer service. */
    @Inject
    private EventInvitationWriterService eventInvitationWriterService;

    /** The event invitation writer service. */
    @Inject
    private EventInvitationService eventInvitationService;

    /**
     *
     * @param page
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/me/invitations")
    public PageResponse<EventInvitation> getUserEventInvitations(
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        LOGGER.debug("Retrieving user event invitations for current user");
        return new PageResponse<>(eventInvitationService.getUserEventInvitations(getUser(), getPageRequest(page)));
    }

    /**
     * Invites user to join an event.
	 *
     * @param event  The event (only id is required).
     * @param userId The user id.
     * @throws InvalidFieldValueException If event have no id.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST, path = "/users/{id}/invitations")
    public EventInvitation invite(@RequestBody Event event, @PathVariable(value = "id") Long userId) throws
        InvalidFieldValueException, UnauthorizedOperationException {
        LOGGER.debug("Invite user:id={} to event:id={}", userId, event.getId());
        return eventInvitationWriterService.inviteUserToEvent(event, getUser(), userId);
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
        eventInvitationWriterService.acceptInvitation(getUser(), eventId);
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
        eventInvitationWriterService.refuseInvitation(getUser(), invitationId);
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
        LOGGER.debug("Cancel invitation from user:id={} for invitation:id={}", userId, invitationId);
        eventInvitationWriterService.cancelInvitation(getUser(), invitationId);
    }

}
