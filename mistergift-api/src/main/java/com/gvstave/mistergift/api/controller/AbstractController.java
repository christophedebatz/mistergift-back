package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.service.TokenService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Base controller that offers some sessions methods.
 */
class AbstractController {

    /** The environment. */
    @Inject
    protected Environment environment;

    /** The current http request context. */
    @Inject
    private HttpServletRequest httpServletRequest;

    /** The token service. */
    @Inject
    private TokenService tokenService;

    /**
     * Returns the current stateless-request user.
     *
     * @return The user.
     */
    protected User getUser() {
        String token = httpServletRequest.getHeader(
            environment.getProperty("token.header.name")
        );

        if (token != null) {
            return tokenService.getUserFromToken(token);
        }

        return null;
    }

    /**
     * Returns a page request for pagination.
     *
     * @param page The requested page number.
     * @return The page request.
     */
    protected PageRequest getPageRequest(int page) {
        return new PageRequest(
            Optional.of(page).map(p -> p - 1 >= 0 ? p - 1 : 0).get(),
            getResultsPageSize()
        );

    }

    /**
     * Returns the results page size.
     *
     * @return The page size.
     */
    private int getResultsPageSize() {
        return Integer.valueOf(
            environment.getProperty("results.page.size")
        );
    }

}
