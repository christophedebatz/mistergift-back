package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import com.gvstave.mistergift.data.domain.jpa.Link;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Link}.
 */
@Repository
public interface LinkRepository extends EntityRepository<Link, Long> {
}
