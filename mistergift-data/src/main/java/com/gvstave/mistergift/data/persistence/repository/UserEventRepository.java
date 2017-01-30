package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.domain.UserEvent;
import com.gvstave.mistergift.data.domain.UserEventId;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link User}.
 */
@Repository
public interface UserEventRepository extends EntityRepository<UserEvent, UserEventId> {
}
