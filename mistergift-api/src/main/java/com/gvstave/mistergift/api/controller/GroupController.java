package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.api.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.api.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.persistence.UserEventPersistenceService;
import com.gvstave.mistergift.data.service.GiftService;
import com.gvstave.mistergift.data.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;

@UserRestricted
@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    /** The event persistence service. */
    @Inject
    private UserEventPersistenceService userEventPersistenceService;

    /** The event service. */
    @Inject
    private EventService eventService;

    /** The gift service. */
    @Inject
    private GiftService giftService;

    /**
     * Default constructor.
     */
    public GroupController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns the list of the groups.
     *
     * @return Serialized groups list.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageResponse<Event> getGroups(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        PageRequest pageRequest = getPageRequest(page);
        //return new PageResponse<>(userEventPersistenceService.findAll(pageRequest));
        return null;
    }

    /**
     * Returns a event by its id.
     *
     * @param id The event id.
     * @return a serialized {@link Event}.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody Event getGroupById(@PathVariable(value = "id") Long id) {
        //return userEventPersistenceService.findOne(id);
        return null;
    }

    /**
     * Save new event in database.
     *
     * @param event The event.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    Event save(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureGroupValid(event, false);
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
    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureGroupValid(event, true);
        LOGGER.debug("Updating event={}", event);
        //userEventPersistenceService.save(event);
    }

    /**
     * Removes a event in database.
     *
     * @param id The event id.
     * @throws UnauthorizedOperationException if the user is not an api of the event.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void deleteGroupById(@PathVariable(value = "id") Long id) throws UnauthorizedOperationException {
        if (!eventService.isEventAdmin(getUser(), id)) {
            throw new UnauthorizedOperationException("remove event");
        }
        LOGGER.debug("Deleting user by id={}", id);
        userEventPersistenceService.delete(id);
    }

    /**
     *
     * @return
     */
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
//    public @ResponseBody PageResponse<Gift> getGroupGiftsForUser(@RequestParam("id") Long id) {
//        Event event = groupPersistenceService.findOne(id);
//        if (event != null) {
//            //Page<Gift> gifts = giftService.getGroupGiftForUser(event, getUser());
//        }
//
//        return null;
//    }

    /**
     * Ensure that given event is correctly hydrated.
     *
     * @param event    The event.
     * @param isUpdate Whether the action implies event-update.
     * @throws UnauthorizedOperationException if operation is not permitted for this event.
     * @throws InvalidFieldValueException     if a field value is invalid.
     */
    private void ensureGroupValid(Event event, boolean isUpdate) throws UnauthorizedOperationException,
            InvalidFieldValueException {
        Objects.requireNonNull(event);

        if (isUpdate && !eventService.isEventAdmin(getUser(), event.getId())) {
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
