package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.service.command.UserGiftWriterService;
import com.gvstave.mistergift.data.service.dto.GiftDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    @Inject
    private UserGiftWriterService userGiftWriterService;

    /**
     * Default constructor.
     */
    public UserGiftController () throws TooManyRequestException {
        super();
    }

    @UserRestricted
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/user/{userId}/gifts", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void update(@RequestBody GiftDto giftDto,
                       @PathVariable(value = "userId") Long userId) throws UnauthorizedOperationException, InvalidFieldValueException {
        LOGGER.debug("Updating gift={}", giftDto);



    }

}
