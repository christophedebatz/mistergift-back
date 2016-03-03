package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Group;
import com.gvstave.mistergift.data.persistence.GroupPersistenceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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
    @UserRestricted
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageResponse<Group> getGroups(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        PageRequest pageRequest = getPageRequest(page);
        return new PageResponse<>(groupPersistenceService.findAll(pageRequest));
    }

}
