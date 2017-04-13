package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.Participation;
import com.gvstave.mistergift.data.domain.jpa.ParticipationPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.QParticipation;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * The {@link Participation} service.
 */
@Service
public class ParticipationService {

    /** The participation persistence service. */
    @Inject
    private ParticipationPersistenceService participationPersistenceService;

    /** The authenticated user. */
    @Inject
    private AuthenticatedUser authenticatedUser;

    /**
     * Returns the gift participations.
     *
     * @param giftId The gift id.
     * @param pageable The pageable.
     * @return The participations.
     */
    @Transactional(readOnly = true)
    public Page<Participation> getGiftParticipations(Long giftId, Pageable pageable) {
        Predicate predicate = QParticipation.participation.gift.id.eq(giftId);
        return participationPersistenceService.findAll(predicate, pageable);
    }

    /**
     * Returns the user participations for a given event.
     *
     * @param eventId The event id.
     * @param pageable The pageable.
     * @return The user participations.
     */
    @Transactional(readOnly = true)
    public Page<Participation> getUserParticipations(Long eventId, Pageable pageable) {
        BooleanExpression predicate = QParticipation.participation.user.eq(authenticatedUser.getUser());
        if (eventId != null) {
            predicate.and(QParticipation.participation.event.id.eq(eventId));
        }
        return participationPersistenceService.findAll(predicate, pageable);
    }

}
