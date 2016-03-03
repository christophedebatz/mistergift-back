package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.admin.controller.exception.FileUploadException;
import com.gvstave.mistergift.admin.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.admin.controller.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.admin.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.FileMetadata;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.FileMetadataPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.UserService;
import com.gvstave.mistergift.service.CroppingService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractController {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user service. */
    @Inject
    private UserService userService;

    /** The file metadata persistence service. */
    @Inject
    private FileMetadataPersistenceService fileMetadataPersistenceService;

    /** The picture cropping service. */
    @Inject
    private CroppingService croppingService;

    /** The Spring environment. */
    @Inject
    private Environment environment;

    /**
     * Returns the list of the users.
     *
     * @return Serialized users list.
     */
    @UserRestricted
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    PageResponse<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
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
    public @ResponseBody User getUserById(@PathVariable(value = "id") Long id) {
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
        ensureUserValid(user, false);
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
        ensureUserValid(user, true);
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
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, path = "/self", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody FileMetadata uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            @RequestParam("cropCoords") Rectangle cropCoords) throws InvalidFieldValueException, FileUploadException {

        if (file == null || file.isEmpty()) {
            throw new InvalidFieldValueException("file");
        }

        String targetDirectory = environment.getProperty("upload.picture.directory");
        String targetFileName = String.format("%s/%s", targetDirectory, UUID.randomUUID().toString());

        try {
            // convert to file
            File pictureFile = new File(targetFileName);
            file.transferTo(pictureFile);

            // crop and save
            croppingService.crop(pictureFile, cropCoords).save();

            // create file-metadate
            User currentUser = getUser();
            FileMetadata profilePicture = new FileMetadata();
            profilePicture.setOwner(currentUser);
            profilePicture.setUrl(environment.getProperty("upload.domain") + targetFileName);

            // save file meta data
            fileMetadataPersistenceService.save(profilePicture);

            // save user
            currentUser.setPicture(profilePicture);
            userPersistenceService.save(currentUser);

            return profilePicture;

        } catch (IOException exception) {
            throw new FileUploadException(file, exception);
        }
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
