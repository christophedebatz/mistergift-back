package com.gvstave.mistergift.data.persistence.jpa.repository;

import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import com.gvstave.mistergift.data.persistence.jpa.service.JpaEntityRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link User}.
 */
@Repository
public interface UserEventRepositoryJpa extends JpaEntityRepository<UserEvent, UserEventId> {
}
