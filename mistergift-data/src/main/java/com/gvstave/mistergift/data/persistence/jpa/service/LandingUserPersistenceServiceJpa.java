package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.LandingUser;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link LandingUser}.
 */
@Service
public class LandingUserPersistenceServiceJpa extends JpaEntityPersistenceService<LandingUser, Long> {

}
