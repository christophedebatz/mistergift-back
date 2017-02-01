package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Gift}.
 */
@Service
public class GiftPersistenceServiceJpa extends JpaEntityPersistenceService<Gift, Long> {

}
