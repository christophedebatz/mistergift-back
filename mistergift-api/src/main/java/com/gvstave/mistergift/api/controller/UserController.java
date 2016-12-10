package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.*;
import com.gvstave.mistergift.data.exception.DuplicatedEntityException;
import com.gvstave.mistergift.data.exception.FileUploadException;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.command.UserWriterService;
import com.gvstave.mistergift.data.service.query.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user service. */
    @Inject
    private UserService userService;

    /** The user writer service. */
    @Inject
    private UserWriterService userWriterService;

    /**
     * Returns the list of the users.
     *
     * @return Serialized users list.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public PageResponse<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        LOGGER.debug("Retrieving users with page={}", page);
        PageRequest pageRequest = getPageRequest(page);
        return new PageResponse<>(userPersistenceService.findAll(pageRequest));
    }


    /**
     * Returns the user that owns the request.
     *
     * @return Serialized user.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/me")
    public User getSelfUser() {
        LOGGER.debug("Retrieving current user");
        return getUser();
    }

    /**
     * Returns a user by its id.
     *
     * @param id The user id.
     * @return Serialized user.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable(value = "id") Long id) {
        LOGGER.debug("Retrieving user by id={}", id);
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
    @RequestMapping(path = "/users", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public User save(@RequestBody User user) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureUserValid(user, false);
        LOGGER.debug("Saving user={}", user);

        Optional<User> existentUser = Optional.ofNullable(
            userPersistenceService.findOne(QUser.user.email.eq(user.getEmail()))
        );

        if (existentUser.isPresent()) {
            throw new DuplicatedEntityException("user", "email");
        }

        return userWriterService.saveOrUpdate(user);
    }

    /**
     * Update user in database.
     *
     * @param user The user.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/me", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@RequestBody User user)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureUserValid(user, true);
        LOGGER.debug("Updating user={}", user);
        userWriterService.saveOrUpdate(user);
    }

    /**
     * Sets the current-user profile picture.
     *
     * @param file The file object.
     * @return The associated file metadata object.
     * @throws InvalidFieldValueException when file is null or empty.
     * @throws FileUploadException when error occurs while uploading.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/me/picture", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public FileMetadata uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("coords") String coords) throws InvalidFieldValueException, FileUploadException {
        LOGGER.debug("Uploading picture={} and cropping={}", file, coords);
        return userWriterService.uploadPicture(getUser(), file, coords);
    }

    /**
     * Returns the user wish list
     *
     * @param page The current page.
     * @return The {@link Product} page.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/me/wishlist")
    public PageResponse<Gift> getUserWishList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        User user = getUser();
        LOGGER.debug("Retrieving user={} viewable products with page={}", user, page);
        // todo
        return null;
    }

	/**
     *
     * @return
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/me/timeline")
    public String getUserTimeline() {
        User user = getUser();
        LOGGER.debug("Retrieving user={} timeline", user);
        return userService.getTimeline(user);
    }

    /**
     * Ensures that given user is correctly hydrated.
     *
     * @param user The user.
     * @param isUpdate Whether we want to update user.
     * @throws UnauthorizedOperationException if the user that asks for action has not enough right to proceed.
     * @throws InvalidFieldValueException if a field to update is null or empty.
     */
    private void ensureUserValid(User user, boolean isUpdate)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        Objects.requireNonNull(user);

        if (isUpdate && (user.getId() != null || user.getId() != getUser().getId())) {
            throw new UnauthorizedOperationException("update user");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidFieldValueException("email");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidFieldValueException("password");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            throw new InvalidFieldValueException("name");
        }

    }

}