package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.domain.QEvent;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.domain.UserEvent;
import com.gvstave.mistergift.data.domain.UserEventId;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    /**
     * The user event filter.
     */
    enum UserEvenFilter {

        /** The relation describes that the user is an admin of the event. */
        ADMIN("admin"),

        /** The relation describes that the user has been invited to join the event. */
        INVITATION("invite"),

        /**
         * The relation describes that the user participates to the event
         * and that all participants can see its gifts.
         */
        CAN_SEE_MINES("can-see-mine"),

        /** The relation describes that the user participates to the event
         * and that he can see the gifts of other participants.
         */
        CAN_SEE_OTHERS("can-see-others");

        /** The filter. */
        String filter;

        /**
         * Constructor.
         *
         * @param filter The filter.
         */
        UserEvenFilter(String filter) {
            this.filter = filter;
        }

        /**
         * Returns the filter.
         *
         * @return The filter.
         */
        public String getFilter () {
            return filter;
        }

    }

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
     * Returns the lists of events that current user is participant.
     *
     * @param page
     * @param filters
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/users/self/events")
    public @ResponseBody
    PageResponse<Map.Entry<String, List<Event>>> getUserEvents(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "filters") String filters) {
        User user = getUser();
        PageRequest pageable = getPageRequest(page);

        List<Map.Entry<String, List<Event>>> events = new ArrayList<>();
        List<String> eventStatus = Arrays.asList(filters.replaceAll("\\s+","").split(","));

        // user-event relation types
        // pending event / invitation from other participants
        String pendingFlag = UserEvenFilter.INVITATION.getFilter();
        if (eventStatus.contains(pendingFlag)) {
            List<Event> inviteEvents = eventService.getUserInvitationEvents(user, pageable);
            events.add(new AbstractMap.SimpleEntry<>(pendingFlag, inviteEvents));
        }

        // event where i'm admin
        String adminFlag = UserEvenFilter.ADMIN.getFilter();
        if (eventStatus.contains(adminFlag)) {
            events.add(new AbstractMap.SimpleEntry<>(adminFlag, eventService.getUserAdminEvents(user, pageable)));
        }

        // event status type
        // cancelled events
        String cancelledFlag = Event.EventStatus.CANCELLED.getStatus();
        if (eventStatus.contains(cancelledFlag)) {
            List<Event> cancelled = eventService.getUserEventsByStatus(user, Event.EventStatus.CANCELLED, pageable);
            events.add(new AbstractMap.SimpleEntry<>(cancelledFlag, cancelled));
        }

        // my unpublished events
        String unpublishedFlag = Event.EventStatus.UNPUBLISHED.getStatus();
        if (eventStatus.contains(unpublishedFlag)) {
            List<Event> unpublished = eventService.getUserEventsByStatus(user, Event.EventStatus.UNPUBLISHED, pageable);
            events.add(new AbstractMap.SimpleEntry<>(unpublishedFlag, unpublished));
        }

        // the published events
        String publishedFlag = Event.EventStatus.PUBLISHED.getStatus();
        if (eventStatus.contains(publishedFlag)) {
            List<Event> published = eventService.getUserEventsByStatus(user, Event.EventStatus.PUBLISHED, pageable);
            events.add(new AbstractMap.SimpleEntry<>(publishedFlag, published));
        }

        return new PageResponse<>(new PageImpl<>(events, pageable, events.size()));
    }

    /**
     * Returns the event users.
     *
     * @param page The current page.
     * @param id The event id.
     * @throws EntityNotFoundException
     * @return The event users.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}/users")
    public @ResponseBody PageResponse<User> getEventUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "id") Long id) {
        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(id));

        if (event.isPresent()) {
            Page<User> users = eventService.getEventUsers(event.get(), getPageRequest(page));
            return new PageResponse<>(users);
        }

        throw new EntityNotFoundException("Unable to retrieve event with id=" + id);
    }

    /**
     * Returns the list of administrators for the given event.
     *
     * @param page The current page.
     * @param id The event id.
     * @throws EntityNotFoundException
     * @return The administrators.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}/admins")
    public @ResponseBody PageResponse<User> getEventAdmins(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "id") Long id) {
        Optional<Event> event = Optional.ofNullable(eventPersistenceService.findOne(id));

        if (event.isPresent()) {
            return new PageResponse<>(eventService.getEventAdmins(event.get(), getPageRequest(page)));
        }

        throw new EntityNotFoundException("Unable to retrieve event with id=" + id);
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
     * Create and save a new event.
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
     * Updates a event.
     *
     * @param event The event.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     * @return The updated event.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT, path = "/events", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Event update(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureEventIsValid(event, true);
        if (!eventService.isUserEventAdmin(getUser(), event.getId())) {
            throw new UnauthorizedOperationException("remove event");
        }
        LOGGER.debug("Updating event id={}", event);
        return eventPersistenceService.save(event);
    }

    /**
     * Updates the event status.
     *
     * @param status
     * @param id
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, path = "/events/{id}/status", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateStatus(@RequestBody String status, @PathVariable(value = "id") Long id) throws UnauthorizedOperationException, InvalidFieldValueException {
        if (!eventService.isUserEventAdmin(getUser(), id)) {
            throw new UnauthorizedOperationException("update event status");
        }
        LOGGER.debug("Updating event id={} status");
        Optional<Event> event = Optional.of(eventPersistenceService.findOne(id));

        // not using ifPresent because non
        if (event.isPresent()) {
            event.get().setStatus(Event.EventStatus.fromString(status));
            eventPersistenceService.save(event.get());
        } else {
            throw new EntityNotFoundException("Unable to retrieve event with id=" + id);
        }
    }

    /**
     * Removes a event.
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
        LOGGER.debug("Deleting event id={}", id);
        eventPersistenceService.delete(id);
    }

    /**
     * Invites user to join an event.
	 *
     * @param event The event (only id is required).
     * @param userId The user id.
     * @throws InvalidFieldValueException If event have no id.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.POST, path = "/users/{id}/events")
    public void inviteUser(@RequestBody Event event, @PathVariable(value = "id") Long userId) throws
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
                UserEvent userEvent = new UserEvent();
                userEvent.setInvitation(true);
                userEvent.setId(new UserEventId(targetEvent.get(), targetUser.get()));
                userEventPersistenceService.save(userEvent);
            } else {
                throw new UnauthorizedOperationException("invite user as a non-admin user");
            }
        } else {
            throw new EntityNotFoundException("Target user or target event has been not found");
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
