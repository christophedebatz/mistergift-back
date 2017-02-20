package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.LandingUser;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link LandingUser}.
 */
@Service
public class LandingUserPersistenceService extends EntityPersistenceService<LandingUser, Long> {

}
