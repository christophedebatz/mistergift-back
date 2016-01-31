package com.debatz.mistergift.admin.controller;

import com.debatz.mistergift.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Manages user auth.
 */
@RestController
public class AuthController {

    /** The user service. */
    @Inject
    private Authenticable userService;

    /**
     * Returns the new logged user.
     *
     * @param email The entered email.
     * @param password The entered password.
     * @return The user.
     */
    @RequestMapping("/login")
    public User login(
            @RequestParam(value="email") String email,
            @RequestParam(value = "password") String password) {

        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

        return userService.auth(email, password);
    }

    @RequestMapping("/logout")
    public Boolean logout() {
        return true;
    }
}
