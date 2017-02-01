package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.Link;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Link}.
 */
@Repository
public interface LinkRepositoryJpa extends JpaEntityRepository<Link, Long> {
}
