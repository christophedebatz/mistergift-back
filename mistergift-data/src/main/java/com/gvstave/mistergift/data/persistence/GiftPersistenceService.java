package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Gift;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Gift}.
 */
@Service
public class GiftPersistenceService extends EntityPersistenceService<Gift, Long> {

}
