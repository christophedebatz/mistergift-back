package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserGift;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.domain.jpa.EventPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserEventPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserGiftPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserPersistenceService;
import com.gvstave.mistergift.data.service.query.UserEventService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserEventController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserEventController.class);

    /** The event repositories service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /** The user events repositories service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The user event service. */
    @Inject
    private UserEventService userEventService;

    /** The whishlist repositories service. */
    @Inject
    private UserGiftPersistenceService userGiftPersistenceService;

    /**
     * Default constructor.
     */
    public UserEventController () throws TooManyRequestException {
        super();
    }

    /**
     * Returns the list of events where current user is involved in.
     *
     * @param page The page.
     * @param filters The filters.
     * @return The events.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/me/events")
    public Map<String, List<Event>> getUserEvents(
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
        @RequestParam(value = "filters", required = false) String filters) {
        LOGGER.debug("Retrieving current-user events for page={} and filter={}", page, filters);

        final PageRequest pageable = getPageRequest(page, limit);
        return userEventService.getUserEvents(getUser(), filters, pageable);
    }

    /**
     * Returns the event members.
     *
     * @param page The current page.
     * @param eventId The event id.
     * @throws EntityNotFoundException
     * @return The event guests.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{eventId}/members")
    public PageResponse<User> getEventsMembers(
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
        @PathVariable(value = "eventId") Long eventId) {

        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(eventId));

        if (event.isPresent()) {
            return new PageResponse<>(userEventService.getEventMembers(event.get(), getPageRequest(page, limit)));
        }

        throw new EntityNotFoundException("Unable to retrieve event with id=" + eventId);
    }

    /**
     * Returns the event guests users.
     *
     * @param page The current page.
     * @param eventId The event id.
     * @throws EntityNotFoundException
     * @return The event invited users.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{eventId}/guests")
    public PageResponse<User> getEventInvitedUsers(
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
        @PathVariable(value = "eventId") Long eventId) {
        LOGGER.debug("Retrieving event invited users for event:id={} and page={}", eventId, page);
        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(eventId));

        if (event.isPresent()) {
            return new PageResponse<>(userEventService.getEventPendingUsers(event.get(), getPageRequest(page, limit)));
        }

        throw new EntityNotFoundException("Unable to retrieve event with id=" + eventId);
    }

    /**
     * Returns the whishlist of an event user according to the current user.
     *
     * @param page    The page.
     * @param eventId The {@link Event} id.
     * @param userId  The {@link User} id.
     * @return The whislist.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{eventId}/users/{userId}/gifts")
    public PageResponse<UserGift> getEventUserGifts(
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
        @PathVariable(value = "eventId") Long eventId,
        @PathVariable(value = "userId") Long userId) {
        LOGGER.debug("Retrieving user event gifts list according current user for event:id={}, user:id={} and page={}", eventId, userId, page);

        BooleanExpression qUserEvent = QUserEvent.userEvent.id.event.id.eq(eventId)
            .and(QUserEvent.userEvent.id.user.id.eq(userId));
        Optional<UserEvent> userEventOpt = Optional.ofNullable(userEventPersistenceService.findOne(qUserEvent));

        if (userEventOpt.isPresent()) {
            UserEvent userEvent = userEventOpt.get();

            if (userEvent.canSeeMines()) {
                BooleanExpression qWhishlist = QUserGift.userGift.id.user.id.eq(userId);
                return new PageResponse<>(userGiftPersistenceService.findAll(qWhishlist, getPageRequest(page, limit)));
            }
        }

        return PageResponse.empty();
    }

    /**
     * Returns the list of administrators for the given event.
     *
     * @param page The current page.
     * @param eventId   The event id.
     * @throws EntityNotFoundException
     * @return The administrators.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{eventId}/admins")
    public PageResponse<User> getEventAdmins(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                             @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
                                             @PathVariable(value = "eventId") Long eventId) {
        LOGGER.debug("Retrieving event administrators for event:id={} and page={}", eventId, page);
        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(eventId));

        if (event.isPresent()) {
            return new PageResponse<>(userEventService.getEventAdmins(event.get(), getPageRequest(page, limit)));
        }

        throw new EntityNotFoundException("Unable to retrieve event with id=" + eventId);
    }

}
