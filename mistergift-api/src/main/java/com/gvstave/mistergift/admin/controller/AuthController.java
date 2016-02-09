package com.gvstave.mistergift.admin.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Manages user auth.
 */
@RestController
public class AuthController {

    /** The authentication manager. */
    @Inject
    private AuthenticationManager authenticationManager;

    /**
     * Returns the new logged user.
     *
     * @param email The entered email.
     * @param password The entered password.
     * @return The user.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Authentication login(
            @RequestParam(value="email") String email,
            @RequestParam(value = "password") String password) {

        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    @RequestMapping("/logout")
    public Boolean logout() {
        return true;
    }
}
