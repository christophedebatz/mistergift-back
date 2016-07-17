package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.api.controller.exception.DuplicatedEntityException;
import com.gvstave.mistergift.api.controller.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.domain.LandingUser;
import com.gvstave.mistergift.data.domain.QLandingUser;
import com.gvstave.mistergift.data.persistence.LandingUserPersistenceService;
import com.gvstave.mistergift.service.geoip.GeolocationResult;
import com.gvstave.mistergift.service.geoip.GeolocationService;
import com.gvstave.mistergift.service.mailing.LandingUserEmailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    /** The geo location service. */
    @Inject
    private GeolocationService geolocationService;

    /** The http servlet request. */
    @Inject
    private HttpServletRequest request;

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
    public @ResponseBody LandingUser save(@RequestBody LandingUser landingUser) throws InvalidFieldValueException, IOException {
        LOGGER.debug("Creating landingUser={}", landingUser);

        if (landingUser.getEmail() == null) {
            throw new InvalidFieldValueException("email");
        }

        // checks if same email already exists
        landingUser.setEmail(landingUser.getEmail().toLowerCase());
        Optional<LandingUser> optUser = Optional.ofNullable(
            landingUserPersistenceService.findOne(QLandingUser.landingUser.email.eq(landingUser.getEmail()))
        );

        if (optUser.isPresent()) {
            throw new DuplicatedEntityException(LandingUser.class.getSimpleName(), "email");
        }

        // prepare and send email
        Map<String, Object> model = new HashMap<>();
        model.put("email", landingUser.getEmail());
        landingUserEmailingService.send(landingUser.getEmail(), model);

        // geolocalize client from its ip address
        String ip = geolocationService.requestClientIp(request);
        Optional<GeolocationResult> geo = geolocationService.requestClientGeolocation(ip);
        landingUser.setIp(ip);

        if (geo.isPresent()) {
            landingUser.setCountry(geo.get().getCountry());
            landingUser.setRegion(geo.get().getRegion());
            landingUser.setCity(geo.get().getCity());
        }

        // save
        return Optional.of(landingUser)
                .map(landingUserPersistenceService::save).get();

    }

}