package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.Group;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.GroupPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * The group service.
 */
@Service
public class GroupService {

    /**
     * The group persistence service.
     */
    @Inject
    private GroupPersistenceService groupPersistenceService;

    /**
     * The user persistence service.
     */
    @Inject
    private UserPersistenceService userPersistenceService;

    /**
     * Creates new group and add it to the given user.
     *
     * @param group The group to create.
     * @return the created group.
     */
    @Transactional
    public Group createGroup(Group group, User user) {
        Objects.requireNonNull(group);
        Objects.requireNonNull(user);

        group.setAdministrators(Collections.singletonList(user));
        group.getMembers().add(user);
        group = groupPersistenceService.save(group);

        return group;
    }

    /**
     * Returns if the given user is an api of the given group id.
     *
     * @param user The user.
     * @param groupId The group id.
     * @return whether the user is part of the group admins.
     */
    public boolean isUserGroupAdmin(User user, Long groupId) {
        Objects.requireNonNull(groupId);

        // if update operation
        Optional<Group> baseGroup = Optional.ofNullable(groupPersistenceService.findOne(groupId));

        // current group must be an api of the group
        return baseGroup.isPresent() &&
                baseGroup.get().getAdministrators().stream()
                        .filter(admin -> admin == user)
                        .count() > 0;
    }

}
