package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.persistence.jpa.service.GiftPersistenceServiceJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * This is the controller for gift requests.
 */
@UserRestricted
@RestController
@RequestMapping(value = "/gifts", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GiftController.class);

    /** The gift persistence service. */
    @Inject
    private GiftPersistenceServiceJpa giftPersistenceService;

    /**
     * Default constructor.
     */
    public GiftController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns a {@link Gift}.
     *
     * @return Serialized gift.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody Gift getGiftDetails(@RequestParam("id") Long id) {
        return giftPersistenceService.findOne(id);
    }

}
