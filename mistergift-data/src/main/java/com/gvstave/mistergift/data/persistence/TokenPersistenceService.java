package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Token;
import org.springframework.stereotype.Service;

/**
 * Persistence service for {@link Token}
 */
@Service
public class TokenPersistenceService extends EntityPersistenceService<Token, String> {

}
