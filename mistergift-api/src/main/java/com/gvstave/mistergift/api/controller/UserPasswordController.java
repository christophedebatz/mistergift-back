package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.service.query.password.PasswordTokenNotFound;
import com.gvstave.mistergift.data.service.query.password.UserNotFoundException;
import com.gvstave.mistergift.data.service.query.password.UserPasswordResult;
import com.gvstave.mistergift.data.service.query.password.UserPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages the requests that refers to when user forgots its password.
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPasswordController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /** The user password service. */
    @Inject
    private UserPasswordService userPasswordService;

    /**
     * Asks for new password when user forgot its password.
     *
     * @param response The servlet response.
     * @param email The email that user entered.
     */
    @RequestMapping(path = "/password/token", method = RequestMethod.GET)
    public void requestNewPassword(HttpServletResponse response, @RequestParam(value = "email") String email) {
        LOGGER.debug("Retrieving user from email={}", email);
        UserPasswordResult result = userPasswordService.requestNewPassword(email);

        // if error when request new password, returns errored status codes
        if (result.isErrored()) {
                response.setStatus(result.getErrorType().equals("mail") ?
                    HttpServletResponse.SC_NOT_FOUND :
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                );
        } else {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
    }

    /**
     * Sets the new user password.
     *
     * @param password The new password.
     * @param token The user resetting password token.
     * @return The new user token.
     * @throws UserNotFoundException If user has been not found.
     * @throws PasswordTokenNotFound If password is not recognized.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/password", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public Token setNewPassword(@RequestBody String password, @RequestParam(value = "token") String token) throws
        UserNotFoundException, PasswordTokenNotFound {
        LOGGER.debug("Settings new password from token={}", token);
        return userPasswordService.setNewPassword(token, password);
    }

}
