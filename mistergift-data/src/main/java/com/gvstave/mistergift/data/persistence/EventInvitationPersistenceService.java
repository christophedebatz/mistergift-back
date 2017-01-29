package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.EventInvitation;
import org.springframework.stereotype.Service;

/**
 * Repository for {@link EventInvitation}.
 */
@Service
public class EventInvitationPersistenceService extends BaseEntityPersistenceService<EventInvitation, Long> {

    /**
     * Constructor.
     */
    protected EventInvitationPersistenceService () {
        super(EventInvitation.class);
    }

}
