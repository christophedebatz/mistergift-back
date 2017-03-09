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
        return new PageRequest(page - 1 >= 0 ? page - 1 : 0, getMaximumResultsSize());
    }

    /**
     * Returns a page request for pagination.
     *
     * @param page The requested page number.
     * @param limit The requested page limit.
     * @return The page request.
     */
    protected PageRequest getPageRequest(int page, int limit) {
        int appliedLimit = limit > getMaximumResultsSize() ? getMaximumResultsSize() : limit;
        return new PageRequest(page - 1 >= 0 ? page - 1 : 0, appliedLimit);
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
