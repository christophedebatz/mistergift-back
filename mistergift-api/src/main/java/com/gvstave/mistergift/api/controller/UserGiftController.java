package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.QUserGift;
import com.gvstave.mistergift.data.domain.UserGift;
import com.gvstave.mistergift.data.persistence.UserGiftPersistenceService;
import com.gvstave.mistergift.data.service.query.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * This is the controller for whishlist requests.
 */
@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGiftController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserGiftController.class);

    /** The user gifts persistence service. */
    @Inject
    private UserGiftPersistenceService userGiftPersistenceService;

    /** The user service. */
    @Inject
    private UserService userService;

    /**
     * Default constructor.
     */
    public UserGiftController () throws TooManyRequestException {
        super();
    }

    /**
     * Returns a user gifts list.
     *
     * @return Serialized gift list.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}/gifts")
    public @ResponseBody PageResponse<UserGift> getUserGifts(@PathVariable("userId") Long userId, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
        LOGGER.debug("Retrieving user gifts list for user-id={} and page={}", userId, page);
        return userService.fromId(userId)
            .map(user -> userGiftPersistenceService.findAll(QUserGift.userGift.id.user.eq(user), getPageRequest(page)))
            .map(PageResponse::new)
            .get();
    }

}