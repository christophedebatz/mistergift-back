package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Link;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Link}.
 */
@Repository
public interface LinkRepository extends EntityRepository<Link, Long> {
}
