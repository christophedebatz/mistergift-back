package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.jpa.UserGift;
import com.gvstave.mistergift.data.domain.jpa.UserGiftId;
import org.springframework.stereotype.Service;

/**
 * Repository for {@link UserGift}.
 */
@Service
public class UserGiftPersistenceService extends EntityPersistenceService<UserGift, UserGiftId> {

}
