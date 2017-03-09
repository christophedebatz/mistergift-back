package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API endpoint for {@GiftComment }.
 */
@UserRestricted
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCommentController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(GiftCommentController.class);


}
