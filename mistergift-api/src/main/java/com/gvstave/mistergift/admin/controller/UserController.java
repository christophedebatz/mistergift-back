package com.gvstave.mistergift.admin.controller;

import javax.inject.Inject;

import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.config.annotation.Get;
import com.gvstave.mistergift.config.annotation.UserOnly;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "/users",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController extends BaseController {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;


    /**
     * Returns the list of the users.
     *
     * @return Serialized users list.
     */
    @Get
    @UserOnly
    public @ResponseBody PageResponse<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") final Integer page) {
        PageRequest pageRequest = getPageRequest(page);
        return new PageResponse<>(userPersistenceService.findAll(pageRequest));
    }


    /**
     * Returns the user that owns the request.
     *
     * @return Serialized user.
     */
    @Get
    @UserOnly
    @RequestMapping(path = "/self")
    public @ResponseBody User getSelfUser() {
        return getUser();
    }

    /**
     * Returns a user by its id.
     *
     * @param id The user id.
     * @return Serialized user.
     */
    @Get
    @UserOnly
    @RequestMapping(path = "/{id}")
    public @ResponseBody User getUserById(@RequestParam(value = "id") final Long id) {
        return userPersistenceService.findOne(id);
    }

}
