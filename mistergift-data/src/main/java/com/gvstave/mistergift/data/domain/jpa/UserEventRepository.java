package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.EntityRepository;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserEvent;
import com.gvstave.mistergift.data.domain.jpa.UserEventId;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link User}.
 */
@Repository
public interface UserEventRepository extends EntityRepository<UserEvent, UserEventId> {
}
