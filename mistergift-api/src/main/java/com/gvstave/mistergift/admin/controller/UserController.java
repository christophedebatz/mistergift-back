package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

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
    @Secured("ROLE_USER")
    @RequestMapping(method = { RequestMethod.GET })
    public @ResponseBody PageResponse<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") final Integer page) {
        return new PageResponse<>(userPersistenceService.findAll(getPageRequest(page)));
    }


    /**
     *  Returns the user that owns the request.
     *
     * @return Serialized user.
     */
    @Secured("ROLE_USER")
    @RequestMapping(path = "/self", method = { RequestMethod.GET })
    public @ResponseBody User getSelfUser() {
        return getUser();
    }

    /**
     * Returns a user by its id.
     *
     * @param id The user id.
     * @return Serialized user.
     */
    @Secured("ROLE_USER")
    @RequestMapping(path = "/{id}", method = { RequestMethod.GET })
    public @ResponseBody User getUserById(@RequestParam(value = "id") final Long id) {
        return userPersistenceService.findOne(id);
    }

}
