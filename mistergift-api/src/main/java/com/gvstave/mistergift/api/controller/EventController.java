package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.api.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.api.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.*;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    /** The event persistence service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user events persistence service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The event service. */
    @Inject
    private EventService eventService;

    /**
     * Default constructor.
     */
    public EventController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns the lists of user events.
     *
     * @return Serialized events list.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/users/self/events")
    public @ResponseBody PageResponse<Event> getUserEvents(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "filters") String filters) {
        User user = getUser();
        List<Event> events = new ArrayList<>();
        List<String> stringTypes = Arrays.asList(filters.replaceAll("\\s+","").split(","));

        if (stringTypes.contains(UserEvent.UserEvenFilter.IS_INVITATION.getName())) {
            events.addAll(eventService.getUserInvitationEvents(user));
        }

        if (stringTypes.contains(UserEvent.UserEvenFilter.IS_ADMIN.getName())) {
            events.addAll(eventService.getUserAdminEvents(user));
        }

        // to do can see me and co.

        Page<Event> content = new PageImpl<>(events, getPageRequest(page), events.size());
        return new PageResponse<>(content);
    }

    /**
     * Returns the event users.
     *
     * @param page The current page.
     * @param id The event id.
     * @return The event users.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}/users")
    public @ResponseBody PageResponse<User> getEventUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "id") Long id) {
        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(id));

        if (event.isPresent()) {
            return new PageResponse<>(eventService.getEventUsers(event.get(), getPageRequest(page)));
        }

        throw new EntityNotFoundException("Unable to retrieve given event");
    }

    /**
     * Returns the list of administrators for the given event.
     *
     * @param page The current page.
     * @param id The event id.
     * @return The administrators.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}/admins")
    public @ResponseBody PageResponse<User> getEventAdmins(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "id") Long id) {
        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(id));

        if (event.isPresent()) {
            return new PageResponse<>(
                eventService.getEventAdmins(event.get(), getPageRequest(page))
            );
        }

        throw new EntityNotFoundException("Unable to retrieve given event");
    }

    /**
     * Returns a event by its id.
     *
     * @param id The event id.
     * @return a serialized {@link Event}.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}")
    public @ResponseBody Event getEventById(@PathVariable(value = "id") Long id) {
        return eventPersistenceService.findOne(id);
    }

    /**
     * Save new event in database.
     *
     * @param event The event.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/events", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Event save(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureEventIsValid(event, false);
        LOGGER.debug("Creating event={}", event);
        return eventService.createEvent(event, getUser());
    }

    /**
     * Updates a event in database.
     *
     * @param event The event.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, path = "/events", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureEventIsValid(event, true);
        LOGGER.debug("Updating event={}", event);
        eventPersistenceService.save(event);
    }

    /**
     * Removes a event in database.
     *
     * @param id The event id.
     * @throws UnauthorizedOperationException if the user is not an api of the event.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/events/{id}")
    public void deleteEvent(@PathVariable(value = "id") Long id) throws UnauthorizedOperationException {
        if (!eventService.isUserEventAdmin(getUser(), id)) {
            throw new UnauthorizedOperationException("remove event");
        }
        LOGGER.debug("Deleting event by id={}", id);
        eventPersistenceService.delete(id);
    }

    /**
     *
     * @param event
     * @param userId
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST, path = "/users/{id}/events")
    public void inviteUser(@RequestBody Event event, @PathVariable(value = "id") Long userId) throws InvalidFieldValueException {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(event);

        if (event.getId() == null) {
            throw new InvalidFieldValueException("event id");
        }

        LOGGER.debug("Invite user:id={} to event:id={}", userId, event.getId());
        Optional<User> targetUser = Optional.ofNullable(userPersistenceService.findOne(userId));
        Optional<Event> targetEvent = Optional.ofNullable(eventPersistenceService.findOne(QEvent.event.eq(event)));

        if (targetEvent.isPresent()) {
            boolean userCanInvit = targetEvent.get().getUserEvents().stream()
                .filter(UserEvent::isAdmin)
                .map(UserEvent::getId)
                .map(UserEventId::getUser)
                .anyMatch(user -> getUser().equals(user));

            if (userCanInvit && targetUser.isPresent()) {
                UserEvent userEvent = new UserEvent();
                userEvent.setInvitation(true);
                userEvent.setId(new UserEventId(targetEvent.get(), targetUser.get()));
                userEventPersistenceService.save(userEvent);
            }
        } else {
            throw new EntityNotFoundException("Target user not found");
        }

    }

    /**
     * Ensure that given event is correctly hydrated.
     *
     * @param event    The event.
     * @param isUpdate Whether the action implies event-update.
     * @throws UnauthorizedOperationException if operation is not permitted for this event.
     * @throws InvalidFieldValueException     if a field value is invalid.
     */
    private void ensureEventIsValid(Event event, boolean isUpdate) throws UnauthorizedOperationException, InvalidFieldValueException {
        Objects.requireNonNull(event);

        if (isUpdate && !eventService.isUserEventAdmin(getUser(), event.getId())) {
            throw new UnauthorizedOperationException("update event");
        }

        if (isUpdate && (event.getId() == null || event.getId() <= 0)) {
            throw new InvalidFieldValueException("id");
        }

        if (event.getName() == null || event.getName().isEmpty()) {
            throw new InvalidFieldValueException("name");
        }

    }

}
