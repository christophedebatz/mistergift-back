package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.UserGift;
import com.gvstave.mistergift.data.domain.jpa.UserGiftId;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link UserGift}.
 */
@Repository
public interface UserGiftRepositoryJpa extends JpaEntityRepository<UserGift, UserGiftId> {
}
