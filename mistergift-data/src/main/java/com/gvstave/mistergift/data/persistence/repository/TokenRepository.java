package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Token;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Token}.
 */
@Repository
public interface TokenRepository extends BaseEntityRepository<Token, String> {
}
