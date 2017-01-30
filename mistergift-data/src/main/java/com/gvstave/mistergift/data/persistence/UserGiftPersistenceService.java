package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.UserGift;
import com.gvstave.mistergift.data.domain.UserGiftId;
import org.springframework.stereotype.Service;

/**
 * Repository for {@link UserGift}.
 */
@Service
public class UserGiftPersistenceService extends EntityPersistenceService<UserGift, UserGiftId> {

}
