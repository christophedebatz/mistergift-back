package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import com.gvstave.mistergift.data.domain.jpa.Token;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Token}.
 */
@Repository
public interface TokenRepository extends EntityRepository<Token, String> {
}
