package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Gift}.
 */
@Repository
public interface GiftRepositoryJpa extends JpaEntityRepository<Gift, Long> {
}
