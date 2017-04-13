package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.ApiException;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.service.dto.ParticipationDto;
import com.gvstave.mistergift.data.service.dto.mapper.ParticipationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service which manages the writing of user gift participation.
 */
@Service
public class ParticipationWriterService {

    /** The participation persistence service. */
    @Inject
    private ParticipationPersistenceService participationPersistenceService;

    /** The gift persistence service .*/
    @Inject
    private GiftPersistenceService giftPersistenceService;

    /** The authenticated user. */
    @Inject
    private AuthenticatedUser authenticatedUser;

    /**
     * Creates a new user gift participation.
     *
     * @param participation The participation to create.
     * @param giftId The participation gift id.
     * @return The new participation.
     */
    @Transactional(readOnly = false)
    public ParticipationDto createParticipation(ParticipationDto participation, Long giftId) {
        User user = authenticatedUser.getUser();
        QParticipation qParticipation = QParticipation.participation;
        final Participation existingParticipation = participationPersistenceService.findOne(qParticipation.user.eq(user)
                .and(qParticipation.gift.id.eq(giftId)));

        if (existingParticipation != null) {
            throw new ApiException("Unable to participate more than once to a gift", HttpStatus.CONFLICT);
        }

        Gift gift = giftPersistenceService.findOne(giftId);

        participation.setGift(gift);
        participation.setUser(user);
        Participation userParticipation = new Participation(participation);
        return ParticipationMapper.toParticipationDto(participationPersistenceService.save(userParticipation));

    }

    /**
     * Updates and returns the participation.
     *
     * @param participation The updated participation.
     * @param giftId The participation gift id.
     * @return The updated participation.
     */
    @Transactional(readOnly = false)
    public ParticipationDto updateParticipation(ParticipationDto participation, Long giftId) {
        QParticipation qParticipation = QParticipation.participation;
        final Participation existingParticipation = participationPersistenceService.findOne(qParticipation.user.eq(authenticatedUser.getUser())
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
