package com.gvstave.mistergift.admin.controller;

import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Gift;
import com.gvstave.mistergift.data.persistence.GiftPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 *
 */
@UserRestricted
@RestController
@RequestMapping(value = "/gifts", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GiftController.class);

    /** The gift persistence service. */
    @Inject
    private GiftPersistenceService giftPersistenceService;

    /**
     * Returns a {@link Gift}.
     *
     * @return Serialized gift.
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public @ResponseBody Gift getGiftDetails(@RequestParam("id") Long id) {
        return giftPersistenceService.findOne(id);
    }

}
