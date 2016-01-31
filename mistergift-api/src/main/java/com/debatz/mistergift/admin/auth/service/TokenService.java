package com.debatz.mistergift.admin.auth.service;

import com.debatz.mistergift.admin.auth.exception.MissingTokenException;
import com.debatz.mistergift.data.persistence.TokenPersistenceService;
import com.debatz.mistergift.data.persistence.UserPersistenceService;
import com.debatz.mistergift.model.Token;
import com.debatz.mistergift.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Objects;


@Service
public class TokenService {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The token persistence service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /**
     *
     * @param userDetails
     * @return
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
     *
     * @param token
     * @return
     */
    public boolean isTokenValid(String token) {
        Objects.requireNonNull(token);
        Token foundToken = tokenPersistenceService.findOne(token);

        return foundToken != null && foundToken.isValid();

    }

    /**
     *
     * @param token
     * @return
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
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRoleName()))
                );
            }
        }

        return null;
    }

}
