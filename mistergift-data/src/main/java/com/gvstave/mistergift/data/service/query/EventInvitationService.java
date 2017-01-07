package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.EventInvitation;
import com.gvstave.mistergift.data.domain.QEventInvitation;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.EventInvitationPersistenceService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Query the user event invitations.
 */
@Service
public class EventInvitationService {

    /** The event invitation persistence service. */
    @Inject
    private EventInvitationPersistenceService eventInvitationPersistenceService;

    /**
     * Returns user event invitations.
     *
     * @param user     The user.
     * @param pageable The pageable.
     * @return The page of user event invitations.
     */
    public Page<EventInvitation> getUserEventInvitations(User user, Pageable pageable) {
        Objects.requireNonNull(user);
        BooleanExpression qEventInvitation = QEventInvitation.eventInvitation.targetUser.eq(user);
        return eventInvitationPersistenceService.findAll(qEventInvitation, pageable);
    }

}
