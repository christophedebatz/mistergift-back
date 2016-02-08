package com.debatz.mistergift.admin.auth.service;

import com.debatz.mistergift.admin.auth.exception.MissingTokenException;
import com.debatz.mistergift.data.persistence.TokenPersistenceService;
import com.debatz.mistergift.data.persistence.UserPersistenceService;
import com.debatz.mistergift.data.domain.Token;
import com.debatz.mistergift.data.domain.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Objects;


/**
 * Manages tokens checking.
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
     * @throws MissingTokenException
     */
    public String getToken(UserDetails userDetails) throws MissingTokenException {
        Objects.requireNonNull(userDetails);
        User user = userPersistenceService.findByEmail(userDetails.getUsername());

        if (user != null && user.getToken() != null && user.getToken().isValid()) {
            return user.getToken().getValue();
        }

        throw new MissingTokenException();
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
    public UserDetails getUserFromToken(String token) {
        Objects.requireNonNull(token);
        Token foundToken = tokenPersistenceService.findOne(token);

        if (foundToken != null) {
            User user = foundToken.getUser();

            if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRoleName())
                    )
                );
            }
        }

        return null;
    }

}
