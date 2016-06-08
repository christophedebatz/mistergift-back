package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.api.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Group;
import com.gvstave.mistergift.data.persistence.GroupPersistenceService;
import com.gvstave.mistergift.data.service.GiftService;
import com.gvstave.mistergift.data.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;

@UserRestricted
@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    /** The group persistence service. */
    @Inject
    private GroupPersistenceService groupPersistenceService;

    /** The group service. */
    @Inject
    private GroupService groupService;

    /** The gift service. */
    @Inject
    private GiftService giftService;

    /**
     * Returns the list of the groups.
     *
     * @return Serialized groups list.
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageResponse<Group> getGroups(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        PageRequest pageRequest = getPageRequest(page);
        return new PageResponse<>(groupPersistenceService.findAll(pageRequest));
    }

    /**
     * Returns a group by its id.
     *
     * @param id The group id.
     * @return a serialized {@link Group}.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody Group getGroupById(@PathVariable(value = "id") Long id) {
        return groupPersistenceService.findOne(id);
    }

    /**
     * Save new group in database.
     *
     * @param group The group.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Group save(@RequestBody Group group) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureGroupValid(group, false);
        LOGGER.debug("Creating group={}", group);
        return groupService.createGroup(group, getUser());
    }

    /**
     * Updates a group in database.
     *
     * @param group The group.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody Group group) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureGroupValid(group, true);
        LOGGER.debug("Updating group={}", group);
        groupPersistenceService.save(group);
    }

    /**
     * Removes a group in database.
     *
     * @param id The group id.
     * @throws UnauthorizedOperationException if the user is not an api of the group.
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void remove(@PathVariable(value = "id") Long id) throws UnauthorizedOperationException {
        if (!groupService.isUserGroupAdmin(getUser(), id)) {
            throw new UnauthorizedOperationException("remove group");
        }
        LOGGER.debug("Deleting user by id={}", id);
        groupPersistenceService.delete(id);
    }

    /**
     *
     * @return
     */
//    @ResponseStatus(HttpStatus.OK)
//    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
//    public @ResponseBody PageResponse<Gift> getGroupGiftsForUser(@RequestParam("id") Long id) {
//        Group group = groupPersistenceService.findOne(id);
//        if (group != null) {
//            //Page<Gift> gifts = giftService.getGroupGiftForUser(group, getUser());
//        }
//
//        return null;
//    }

    /**
     * Ensure that given group is correctly hydrated.
     *
     * @param group    The group.
     * @param isUpdate Whether the action implies group-update.
     * @throws UnauthorizedOperationException if operation is not permitted for this group.
     * @throws InvalidFieldValueException     if a field value is invalid.
     */
    private void ensureGroupValid(Group group, boolean isUpdate) throws UnauthorizedOperationException,
            InvalidFieldValueException {
        Objects.requireNonNull(group);

        if (isUpdate && !groupService.isUserGroupAdmin(getUser(), group.getId())) {
            throw new UnauthorizedOperationException("update group");
        }

        if (isUpdate && (group.getId() == null || group.getId() <= 0)) {
            throw new InvalidFieldValueException("id");
        }

        if (group.getName() == null || group.getName().isEmpty()) {
            throw new InvalidFieldValueException("name");
        }

        if (group.getAdministrators() == null || group.getAdministrators().isEmpty()) {
            throw new InvalidFieldValueException("administrators");
        }
    }

}
