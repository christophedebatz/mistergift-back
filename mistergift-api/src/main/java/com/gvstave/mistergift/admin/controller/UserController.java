package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.admin.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.admin.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user service. */
    @Inject
    private UserService userService;


    /**
     * Returns the list of the users.
     *
     * @return Serialized users list.
     */
    @UserRestricted
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    PageResponse<User> getUsers(
            @RequestParam(value = "page", required = false, defaultValue = "1") final Integer page) {
        PageRequest pageRequest = getPageRequest(page);
        return new PageResponse<>(userPersistenceService.findAll(pageRequest));
    }


    /**
     * Returns the user that owns the request.
     *
     * @return Serialized user.
     */
    @UserRestricted
    @RequestMapping(method = RequestMethod.GET, path = "/self")
    public @ResponseBody
    User getSelfUser() {
        return getUser();
    }

    /**
     * Returns a user by its id.
     *
     * @param id The user id.
     * @return Serialized user.
     */
    @UserRestricted
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody User getUserById(@RequestParam(value = "id") final Long id) {
        return userPersistenceService.findOne(id);
    }

    /**
     * Save new user in database.
     *
     * @param user The user.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody User save(@RequestBody User user)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureValidUser(user, false);
        return userService.saveOrUpdate(user);
    }

    /**
     * Update user in database.
     *
     * @param user The user.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@RequestBody User user)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureValidUser(user, true);
        userService.saveOrUpdate(user);
    }

    /**
     * Ensures that given user is correctly hydrated.
     *
     * @param user The user.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    private void ensureValidUser(User user, boolean isUpdate)
            throws UnauthorizedOperationException, InvalidFieldValueException {

        Objects.requireNonNull(user);

        if (isUpdate && user.getId() != getUser().getId()) {
            throw new UnauthorizedOperationException("update user");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidFieldValueException("email");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidFieldValueException("password");
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new InvalidFieldValueException("first name");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new InvalidFieldValueException("last name");
        }
    }

}
