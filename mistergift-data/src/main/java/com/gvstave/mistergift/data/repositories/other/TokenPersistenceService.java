package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.Token;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Token}
 */
@Service
public class TokenPersistenceService extends EntityPersistenceService<Token, String> {

}
