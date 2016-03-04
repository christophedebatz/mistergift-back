package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.admin.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.admin.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Group;
import com.gvstave.mistergift.data.persistence.GroupPersistenceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@UserRestricted
@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController extends AbstractController {

    /** The group persistence service. */
    @Inject
    private GroupPersistenceService groupPersistenceService;

    /**
     * Returns the list of the groups.
     *
     * @return Serialized groups list.
     */

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageResponse<Group> getGroups(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
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
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody Group save(@RequestBody Group group) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureGroupValid(group, false);
        return groupPersistenceService.save(group);
    }

    /**
     *
     * @param group
     * @param isUpdate
     */
    private void ensureGroupValid(Group group, boolean isUpdate) {
    }

}
