package com.gvstave.mistergift.data.service;

import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.TokenPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Objects;

/**
 * The token service.
 */
@Service
public class TokenService {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The token persistence service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /**
     * Returns the token associated with a user.
     *
     * @param userDetails The user.
     * @return The token.
     */
    public String getToken(UserDetails userDetails) {
        Objects.requireNonNull(userDetails);
        User user = userPersistenceService.findByEmail(userDetails.getUsername());

        if (user != null && user.getToken() != null && user.getToken().isValid()) {
            return user.getToken().getId();
        }

        return null;
    }

    /**
     * Returns token from token value.
     *
     * @param token The token value.
     * @return The token.
     */
    public Token getToken(String token) {
        Objects.requireNonNull(token);
        return tokenPersistenceService.findOne(token);
    }

    /**
     * Returns if the given token is currently valid.
     *
     * @param token The token.
     * @return True or false if token is valid or not.
     */
    public boolean isTokenValid(String token) {
        Objects.requireNonNull(token);
        Token foundToken = tokenPersistenceService.findOne(token);

        return foundToken != null && foundToken.isValid();
    }

    /**
     * Returns the token associated user.
     *
     * @param token The token.
     * @return The user.
     */
    public UserDetails getUserFromToken(Token token) {
        User user = token.getUser();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.unmodifiableList(
                            Collections.singletonList(
                                    new SimpleGrantedAuthority(user.getRole().getName().toUpperCase())
                            )
                    )
            );
        }

        return null;
    }

    /**
     * Returns the token associated user.
     *
     * @param token The token.
     * @return The user.
     */
    public User getUserFromToken(String token) {
        Objects.requireNonNull(token);
        Token foundToken = tokenPersistenceService.findOne(token);

        if (foundToken != null) {
            return foundToken.getUser();
        }

        return null;
    }

}
