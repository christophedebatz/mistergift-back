package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.api.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.domain.LandingUser;
import com.gvstave.mistergift.data.persistence.LandingUserPersistenceService;
import com.gvstave.mistergift.service.mailing.LandingUserEmailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/landing", produces = MediaType.APPLICATION_JSON_VALUE)
public class LandingUserController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(LandingUserController.class);

    /** The landing user persistence service. */
    @Inject
    private LandingUserPersistenceService landingUserPersistenceService;

    /** The landing user emailing service. */
    @Inject
    private LandingUserEmailingService landingUserEmailingService;

    /**
     * Default constructor.
     */
    public LandingUserController() throws TooManyRequestException {
        super();
    }

    /**
     * Save new landingUser in database.
     *
     * @param landingUser The landingUser.
     * @throws InvalidFieldValueException If the save has failed.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody LandingUser save(@RequestBody LandingUser landingUser) throws InvalidFieldValueException {
        LOGGER.debug("Creating landingUser={}", landingUser);

        Map<String, Object> variables = new HashMap<>();
        variables.put("email", landingUser.getEmail());

        // send email
        landingUserEmailingService.send(
            getEnv().getProperty("website.email"),
            new String[] { landingUser.getEmail() },
            variables
        );

        return Optional.of(landingUser).map(landingUserPersistenceService::save).get();
    }

}
