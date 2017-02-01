package com.gvstave.mistergift.data.persistence.jpa.service;

import com.gvstave.mistergift.data.domain.jpa.UserGift;
import com.gvstave.mistergift.data.domain.jpa.UserGiftId;
import org.springframework.stereotype.Service;

/**
 * Repository for {@link UserGift}.
 */
@Service
public class UserGiftPersistenceServiceJpa extends JpaEntityPersistenceService<UserGift, UserGiftId> {

}
