package com.gvstave.mistergift.api.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.gvstave.mistergift.api.auth.exception.UserNotFoundException;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.service.query.TokenService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.AbstractMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    /** The current logged-in users cache. */
    private Cache<String, User> users = CacheBuilder.newBuilder()
                    .expireAfterWrite(10, TimeUnit.MINUTES)
                    .maximumSize(1000)
                    .build();

    /**
     * Returns the current stateless-request user.
     *
     * @return The user.
     */
    protected User getUser() throws UserNotFoundException {
        return Optional.ofNullable(httpServletRequest.getHeader(env.getProperty("token.header.name")))
            .map(token -> new AbstractMap.SimpleEntry<>(token, tryGetCachedUser(token)))
            .map(entry -> { users.put(entry.getKey(), entry.getValue()); return entry.getValue(); })
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
        return new PageRequest(Optional.of(page).map(p -> p - 1 >= 0 ? p - 1 : 0).get(), getMaximumResultsPageSize());

    }

    /**
     * Try to retrieve user from cache or get it from database.
     *
     * @param token The user token.
     * @return The user or null.
     */
    private User tryGetCachedUser (String token) {
        return Optional.ofNullable(users.getIfPresent(token)).orElseGet(() -> tokenService.getUserFromToken(token));
    }

    /**
     * Returns the results page size.
     *
     * @return The page size.
     */
    private int getMaximumResultsPageSize () {
        return Integer.valueOf(
            env.getProperty("results.page.size")
        );
    }

}
