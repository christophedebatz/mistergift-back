package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.Token;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Token}
 */
@Service
public class TokenPersistenceServiceJpa extends JpaEntityPersistenceService<Token, String> {

}
