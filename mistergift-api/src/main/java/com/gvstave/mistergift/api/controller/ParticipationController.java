package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.service.dto.ParticipationDto;
import com.gvstave.mistergift.data.service.dto.mapper.ParticipationMapper;
import com.gvstave.mistergift.data.utils.Streams;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the controller for gift requests.
 */
@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ParticipationController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(ParticipationController.class);

    /** The gift persistence service. */
    @Inject
    private GiftPersistenceService giftPersistenceService;

    /** The participation persistence service. */
    @Inject
    private ParticipationPersistenceService participationPersistenceService;

    /**
     * Default constructor.
     */
    public ParticipationController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns the participation of the given user for a gift.
     *
     * @return The user participation.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/gifts/{giftId}/participation")
    public @ResponseBody Participation getUserGiftParticipation(
            @PathVariable("giftId") Long giftId,
            @PathVariable("userId") Long userId) {
        LOGGER.debug("Retrieving current user gift participation for gift-id={} and user-id={}", giftId, userId);
        Predicate predicate = QParticipation.participation.gift.id.eq(giftId)
                .and(QParticipation.participation.user.id.eq(userId));
        return participationPersistenceService.findOne(predicate);
    }

    /**
     * Returns the list of participations for the given gift.
     *
     * @param giftId The gift id.
     * @return The participations.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/gifts/{giftId}/participations")
    public @ResponseBody PageResponse<Participation> getGiftParticipation(
            @PathVariable("giftId") Long giftId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        LOGGER.debug("Retrieving all participations for gift-id={}", giftId);
        Predicate predicate = QParticipation.participation.gift.id.eq(giftId);
        return new PageResponse<>(participationPersistenceService.findAll(predicate, getPageRequest(page, limit)));
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
        participation.setUser(getUser());
        Participation userParticipation = new Participation(participation);
        return ParticipationMapper.toParticipationDto(participationPersistenceService.save(userParticipation));
    }

}
