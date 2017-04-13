package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.jpa.Participation;
import com.gvstave.mistergift.data.domain.jpa.ParticipationPersistenceService;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.service.command.ParticipationWriterService;
import com.gvstave.mistergift.data.service.dto.ParticipationDto;
import com.gvstave.mistergift.data.service.query.ParticipationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * This is the controller for participation requests.
 */
@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ParticipationController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(ParticipationController.class);

    /** The participation persistence service. */
    @Inject
    private ParticipationPersistenceService participationPersistenceService;

    /** The participation writer service. */
    @Inject
    private ParticipationWriterService participationWriterService;

    /** The participation service. */
    @Inject
    private ParticipationService participationService;

    /**
     * Default constructor.
     */
    public ParticipationController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns the list of participations for the given gift.
     *
     * @param giftId The gift id@.
     * @return The participations.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/gifts/{giftId}/participations")
    public @ResponseBody PageResponse<Participation> getGiftParticipation(
            @PathVariable("giftId") Long giftId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        LOGGER.debug("Retrieving all participation for gift-id={}", giftId);
        return new PageResponse<>(participationService.getGiftParticipations(giftId, getPageRequest(page, limit)));
    }

    /**
     * Returns the list of participation of the current user and, eventually for an event.
     *
     * @param eventId The event id (optional).
     * @param page The page.
     * @param limit The limit.
     * @return The list of user participation.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/me/participations")
    public @ResponseBody PageResponse<Participation> getUserParticipations(
            @RequestParam(value = "eventId", required = false) Long eventId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        LOGGER.debug("Retrieving current user participation for eventId={}", eventId);
        return new PageResponse<>(participationService.getUserParticipations(eventId, getPageRequest(page, limit)));
    }

    /**
     * Creates a new participation for the current user.
     *
     * @param giftId The gift id that the user want to participe.
     * @param participation The created participation.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/gifts/{giftId}/participations")
    public @ResponseBody ParticipationDto participate(
            @PathVariable("giftId") Long giftId,
            @RequestBody ParticipationDto participation) {
        LOGGER.debug("Creating new participation for gift-id={} with participation={}", giftId, participation);
        return participationWriterService.createParticipation(participation, giftId);
    }

    /**
     * Updates a participation.
     *
     * @param giftId The gift id.
     * @param participation The participation to be updated.
     * @return The new participation.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/gifts/{giftId}/participations")
    public @ResponseBody ParticipationDto updateParticipation(
            @PathVariable("giftId") Long giftId,
            @RequestBody ParticipationDto participation) {
        LOGGER.debug("Updating gift-id={} with participation={}", giftId, participation);
        return participationWriterService.updateParticipation(participation, giftId);
    }

}
