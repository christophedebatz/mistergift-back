package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.persistence.EventPersistenceService;
import com.gvstave.mistergift.data.service.command.EventWriterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    /** The event persistence service. */
    @Inject
    private EventPersistenceService eventPersistenceService;

    /** The event writer service. */
    @Inject
    private EventWriterService eventWriterService;

    /**
     * Default constructor.
     */
    public EventController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns a event by its id.
     *
     * @param id The event id.
     * @return a serialized {@link Event}.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/events/{id}")
    public Event getEventById(@PathVariable(value = "id") Long id) {
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
    public Event save(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        LOGGER.debug("Creating event={}", event);
        return eventWriterService.createEvent(event, getUser());
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
    public Event update(@RequestBody Event event) throws UnauthorizedOperationException, InvalidFieldValueException {
        LOGGER.debug("Updating event id={}", event);
        return eventWriterService.updateEvent(event, getUser());
    }

    /**
     * Updates the event status.
     *
     * @param status The event status.
     * @param id The event id.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, path = "/events/{id}/status", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateStatus(@RequestBody String status, @PathVariable(value = "id") Long id) throws UnauthorizedOperationException, InvalidFieldValueException {
        LOGGER.debug("Updating event id={} status");
        eventWriterService.updateStatus(id, status, getUser());
    }

    /**
     * Removes an event.
     *
     * @param id The event id.
     * @throws UnauthorizedOperationException if the user is not an api of the event.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, path = "/events/{id}")
    public void deleteEvent(@PathVariable(value = "id") Long id) throws UnauthorizedOperationException {
        LOGGER.debug("Deleting event id={}", id);
        eventWriterService.deleteEvent(id, getUser());
    }

}
