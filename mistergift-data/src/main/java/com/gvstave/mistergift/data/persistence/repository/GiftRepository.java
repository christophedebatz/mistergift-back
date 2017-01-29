package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Gift;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Gift}.
 */
@Repository
public interface GiftRepository extends BaseEntityRepository<Gift, Long> {
}
