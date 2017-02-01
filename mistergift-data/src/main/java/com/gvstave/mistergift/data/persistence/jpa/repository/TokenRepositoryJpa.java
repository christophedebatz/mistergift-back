package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.Token;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Token}.
 */
@Repository
public interface TokenRepositoryJpa extends JpaEntityRepository<Token, String> {
}
