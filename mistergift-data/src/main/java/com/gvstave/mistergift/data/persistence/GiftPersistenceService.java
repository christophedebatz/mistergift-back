package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Gift;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Gift}.
 */
@Service
public class GiftPersistenceService extends BaseEntityPersistenceService<Gift, Long> {

    /**
     * Constructor.
     */
    public GiftPersistenceService() {
        super(Gift.class);
    }

}
