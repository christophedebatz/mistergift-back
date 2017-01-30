package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.UserGift;
import com.gvstave.mistergift.data.domain.UserGiftId;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link UserGift}.
 */
@Repository
public interface UserGiftRepository extends EntityRepository<UserGift, UserGiftId> {
}
