package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.admin.access.PublicAccessGuardian;
import com.gvstave.mistergift.admin.access.exception.TooManyRequestException;
import com.gvstave.mistergift.admin.controller.exception.FileUploadException;
import com.gvstave.mistergift.admin.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.admin.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.FileMetadata;
import com.gvstave.mistergift.data.domain.Gift;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.FileMetadataPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.GiftService;
import com.gvstave.mistergift.data.service.UserService;
import com.gvstave.mistergift.service.CroppingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user service. */
    @Inject
    private UserService userService;

    /** The gift service. */
    @Inject
    private GiftService giftService;

    /** The file metadata persistence service. */
    @Inject
    private FileMetadataPersistenceService fileMetadataPersistenceService;

    /** The picture cropping service. */
    @Inject
    private CroppingService croppingService;

    /** The Spring environment. */
    @Inject
    private Environment environment;

    /** The multiple access voter. */
    @Inject
    private PublicAccessGuardian publicAccessGuardian;

    /**
     * Returns the list of the users.
     *
     * @return Serialized users list.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody PageResponse<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
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
    @RequestMapping(method = RequestMethod.GET, path = "/self")
    public @ResponseBody User getSelfUser() {
        User user = getUser();
        LOGGER.debug("Retrieving current user with id={}", user.getId());
        return user;
    }

    /**
     * Returns a user by its id.
     *
     * @param id The user id.
     * @return Serialized user.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody User getUserById(@PathVariable(value = "id") Long id) {
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
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody User save(@RequestBody User user, HttpServletRequest httpRequest) throws
            UnauthorizedOperationException, InvalidFieldValueException, TooManyRequestException {
        ensureUserValid(user, false);
        publicAccessGuardian.check(httpRequest);
        LOGGER.debug("Saving user={}", user);
        return userService.saveOrUpdate(user);
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
    @RequestMapping(method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@RequestBody User user)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureUserValid(user, true);
        LOGGER.debug("Updating user={}", user);
        userService.saveOrUpdate(user);
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
    @RequestMapping(method = RequestMethod.POST, path = "/self", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public @ResponseBody FileMetadata uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("coords") String coords) throws InvalidFieldValueException, FileUploadException {
        LOGGER.debug("Uploading picture={} and cropping={}", file, coords);

        if (file == null || file.isEmpty()) {
            throw new InvalidFieldValueException("file");
        }

        Rectangle croppingCoords = parseCroppingCoords(coords);
        String extension = environment.getProperty("upload.picture.extension");

        String targetFileName = String.format("%s/%s.%s",
            environment.getProperty("upload.picture.directory"),
            UUID.randomUUID().toString(),
            extension
        );

        try {
            // convert to file
            File pictureFile = new File(targetFileName);
            file.transferTo(pictureFile);

            // crop and save
            croppingService.crop(pictureFile, croppingCoords).save(extension);

            // create file-metadata
            User currentUser = getUser();
            FileMetadata profilePicture = new FileMetadata();
            profilePicture.setOwner(currentUser);
            profilePicture.setUrl(environment.getProperty("upload.domain") + targetFileName);

            // save file meta data
            profilePicture = fileMetadataPersistenceService.save(profilePicture);

            // save user
            currentUser.setPicture(profilePicture);
            userPersistenceService.save(currentUser);

            return profilePicture;

        } catch (IOException exception) {
            throw new FileUploadException(file, exception);
        }
    }

    /**
     * Returns the user gifts.
     *
     * @param page The current page.
     * @return The gifts page.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/self/gifts")
    public @ResponseBody PageResponse<Gift> getUserGifts(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        User user = getUser();
        LOGGER.debug("Retrieving user={} viewable gifts with page={}", user, page);
        PageRequest pageRequest = getPageRequest(page);
        return new PageResponse<>(giftService.getUserGifts(user, pageRequest));
    }

    /**
     * Returns a Rectangle from String coordinates.
     *
     * @param stringCoords The coordinates in string format.
     * @return The rectangle.
     * @throws InvalidFieldValueException whether the coordinate string is invalid.
     */
    private Rectangle parseCroppingCoords(String stringCoords) throws InvalidFieldValueException {

        List<Integer> coords = Arrays.asList(stringCoords.split("\\s")).stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());

        if (coords.size() < 4) {
            throw new InvalidFieldValueException("coords");
        }

        return new Rectangle(coords.get(0), coords.get(1), coords.get(2), coords.get(3));
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

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new InvalidFieldValueException("first name");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new InvalidFieldValueException("last name");
        }
    }

}