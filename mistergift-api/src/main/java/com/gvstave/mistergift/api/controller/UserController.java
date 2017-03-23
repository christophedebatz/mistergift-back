package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.exception.FileUploadException;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.service.command.UserWriterService;
import com.gvstave.mistergift.data.service.dto.UserDto;
import com.gvstave.mistergift.data.service.query.EventService;
import com.gvstave.mistergift.data.service.query.ProductService;
import com.gvstave.mistergift.data.service.query.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /** The user repositories service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The user service. */
    @Inject
    private UserService userService;

    @Inject
    private EventService eventService;

    @Inject
    private ProductService productService;

    @Inject
    private GiftPersistenceService giftPersistenceService;

    @Inject
    private UserGiftPersistenceService userGiftPersistenceService;

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
    public PageResponse<User> getUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        LOGGER.debug("Retrieving users with page={}", page);
        PageRequest pageRequest = getPageRequest(page, limit);
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
     * @param userDto The user dto.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/users", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public User save(@RequestBody UserDto userDto) throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureUserValid(userDto, false);
        LOGGER.debug("Saving user={}", userDto);
        return userWriterService.createUser(userDto);
    }

    /**
     * Update user in database.
     *
     * @param userDto The user.
     * @throws UnauthorizedOperationException
     * @throws InvalidFieldValueException
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/me", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@RequestBody UserDto userDto)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        ensureUserValid(userDto, true);
        LOGGER.debug("Updating user={}", userDto);
        userWriterService.saveOrUpdate(userDto);
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
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/gifts")
    public PageResponse<Product> getUserGifts(
            @PathVariable(value = "id") Long userId,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit) {
        LOGGER.debug("Retrieving user-id={} gifts list with page={} and limit={}", userId, page, limit);

        boolean userCanAskGifts = userService.fromId(userId)
                .filter(user -> !eventService.retrieveCommonUsersEvents(userId, getUser()).isEmpty())
                .isPresent();

        if (userCanAskGifts) {
            PageRequest pageable = getPageRequest(page, limit);
            List<Gift> gifts = giftPersistenceService.findAll(QGift.gift.owner.id.eq(userId), pageable).getContent();
            List<String> giftsProductsIds = gifts.stream().map(Gift::getProductId).collect(Collectors.toList());
            return new PageResponse<>(productService.findByIdsIn(giftsProductsIds, pageable));
        }

        return PageResponse.empty();
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
     * @param userDto The user.
     * @param isUpdate Whether we want to update user.
     * @throws UnauthorizedOperationException if the user that asks for action has not enough right to proceed.
     * @throws InvalidFieldValueException if a field to update is null or empty.
     */
    private void ensureUserValid(UserDto userDto, boolean isUpdate)
            throws UnauthorizedOperationException, InvalidFieldValueException {
        Objects.requireNonNull(userDto);

        if (isUpdate && (userDto.getId() != null || !Objects.equals(userDto.getId(), getUser().getId()))) {
            throw new UnauthorizedOperationException("update user");
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new InvalidFieldValueException("password");
        }

        if (userDto.getKey() == null) {
            if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
                throw new InvalidFieldValueException("email");
            }

            if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
                throw new InvalidFieldValueException("firstName");
            }

            if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
                throw new InvalidFieldValueException("lastName");
            }
        }

    }

}