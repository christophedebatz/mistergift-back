package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Gift}.
 */
@Service
public class GiftPersistenceService extends EntityPersistenceService<Gift, Long> {

}
