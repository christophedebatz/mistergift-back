package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.Group;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.GroupPersistenceService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
@Service
public class GroupService {

    /**
     *
     */
    @Inject
    private GroupPersistenceService groupPersistenceService;

    /**
     *
     * @param user
     * @param groupId
     * @return
     */
    public boolean isUserGroupAdmin(User user, Long groupId) {
        Objects.requireNonNull(groupId);

        // if update operation
        Optional<Group> baseGroup = Optional.ofNullable(groupPersistenceService.findOne(groupId));

        // current group must be an admin of the group
        return baseGroup.isPresent() &&
                baseGroup.get().getAdministrators().stream()
                        .filter(admin -> admin == user)
                        .count() > 0;
    }
}
