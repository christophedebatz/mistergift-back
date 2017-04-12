package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.ApiException;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.service.dto.ParticipationDto;
import com.gvstave.mistergift.data.service.dto.mapper.ParticipationMapper;
import com.gvstave.mistergift.data.utils.Streams;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
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
     * @param giftId The gift id@.
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
     * Returns the list of participations of the current user and, eventually for an event.
     *
     * @param eventId The event id (optional).
     * @param page The page.
     * @param limit The limit.
     * @return The list of user participations.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/me/participations")
    public @ResponseBody PageResponse<Participation> getUserParticipations(
            @RequestParam(value = "eventId", required = false) Long eventId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        LOGGER.debug("Retrieving current user participations for eventId={}", eventId);
        BooleanExpression predicate = QParticipation.participation.user.eq(getUser());
        if (eventId != null) {
            predicate.and(QParticipation.participation.event.id.eq(eventId));
        }
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
        QParticipation qParticipation = QParticipation.participation;
        final Participation existingParticipation = participationPersistenceService.findOne(qParticipation.user.eq(getUser())
                .and(qParticipation.gift.id.eq(giftId)));

        if (existingParticipation != null) {
            throw new ApiException("Unable to participate more than once to a gift", HttpStatus.CONFLICT);
        }

        participation.setUser(getUser());
        Participation userParticipation = new Participation(participation);
        return ParticipationMapper.toParticipationDto(participationPersistenceService.save(userParticipation));
    }

    /**
     * Update a participation.
     *
     * @param giftId The gift id.
     * @param participation The participation to be updated.
     * @return The new participation.
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/gifts/{giftId}/participations")
    public @ResponseBody ParticipationDto updateParticipation(
            @PathVariable("giftId") Long giftId,
            @RequestBody ParticipationDto participation) {
        QParticipation qParticipation = QParticipation.participation;
        final Participation existingParticipation = participationPersistenceService.findOne(qParticipation.user.eq(getUser())
                .and(qParticipation.gift.id.eq(giftId)));

        if (existingParticipation == null) {
            throw new ApiException("Cannot find any participation", HttpStatus.NOT_FOUND);
        }
        Optional.ofNullable(participation.getUser()).ifPresent(existingParticipation::setUser);
        Optional.ofNullable(participation.getEvent()).ifPresent(existingParticipation::setEvent);
        Optional.ofNullable(participation.getGift()).ifPresent(existingParticipation::setGift);
        Optional.ofNullable(participation.getType()).ifPresent(existingParticipation::setType);
        Optional.ofNullable(participation.getValue()).ifPresent(existingParticipation::setValue);
        return ParticipationMapper.toParticipationDto(participationPersistenceService.save(existingParticipation));
    }

}
