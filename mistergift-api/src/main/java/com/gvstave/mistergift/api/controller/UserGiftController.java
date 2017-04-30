package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.service.command.UserGiftWriterService;
import com.gvstave.mistergift.data.service.dto.UserGiftDto;
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

    /** The user gift persistence writer service. */
    @Inject
    private UserGiftWriterService userGiftWriterService;

    /**
     * Default constructor.
     */
    public UserGiftController () throws TooManyRequestException {
        super();
    }


    /**
     * Inserts new product.
     *
     * @param userGiftDto The product.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/me/gifts", method = RequestMethod.POST)
    public UserGiftDto insertNewUserGift(@RequestBody final UserGiftDto userGiftDto) {
        LOGGER.debug("Inserting new gift", userGiftDto);
        return userGiftWriterService.createNewUserGift(userGiftDto);
    }

}
