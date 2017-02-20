package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Gift}.
 */
@Repository
public interface GiftRepository extends EntityRepository<Gift, Long> {
}
