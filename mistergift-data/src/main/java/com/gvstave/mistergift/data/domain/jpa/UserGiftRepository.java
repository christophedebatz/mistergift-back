package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import com.gvstave.mistergift.data.domain.jpa.UserGift;
import com.gvstave.mistergift.data.domain.jpa.UserGiftId;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link UserGift}.
 */
@Repository
public interface UserGiftRepository extends EntityRepository<UserGift, UserGiftId> {
}
