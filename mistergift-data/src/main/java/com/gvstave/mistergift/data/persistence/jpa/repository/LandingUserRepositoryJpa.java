package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.LandingUser;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link LandingUser}.
 */
@Repository
public interface LandingUserRepositoryJpa extends JpaEntityRepository<LandingUser, Long> {
}
