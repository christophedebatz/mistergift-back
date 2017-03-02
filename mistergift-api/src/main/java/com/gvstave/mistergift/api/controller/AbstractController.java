package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.auth.exception.UserNotFoundException;
import com.gvstave.mistergift.data.service.AuthenticatedUser;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.service.query.TokenService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Base controller that offers some sessions methods.
 */
class AbstractController {

    /** The env. */
    @Inject
    protected Environment env;

    /** The current http request context. */
    @Inject
    private HttpServletRequest httpServletRequest;

    /** The token service. */
    @Inject
    private TokenService tokenService;

    /** The authenticated user. */
    @Inject
    private AuthenticatedUser authenticatedUser;

    /**
     * Returns the current stateless-request user.
     *
     * @return The user.
     */
    protected User getUser() throws UserNotFoundException {
        return Optional.ofNullable(authenticatedUser.getUser())
            .orElseThrow(UserNotFoundException::new);
    }

    /**
     * Returns the env.
     *
     * @return The env.
     */
    protected Environment getEnv() {
        return env;
    }

    /**
     * Returns a page request for pagination.
     *
     * @param page The requested page number.
     * @return The page request.
     */
    protected PageRequest getPageRequest(int page) {
        return new PageRequest(Optional.of(page).map(p -> p - 1 >= 0 ? p - 1 : 0).get(), getMaximumResultsSize());

    }

    /**
     * Returns the results page size.
     *
     * @return The page size.
     */
    private int getMaximumResultsSize () {
        return Integer.valueOf(
            env.getProperty("results.page.size")
        );
    }

}
