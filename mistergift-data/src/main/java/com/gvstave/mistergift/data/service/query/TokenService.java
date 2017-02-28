package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.Token;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.TokenPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.UserPersistenceService;
import org.joda.time.DateTime;
import org.springframework.core.env.Environment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * The token service.
 */
@Service
public class TokenService {

    /** The user repositories service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The token repositories service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /** The environment. */
    @Inject
    private Environment env;

    /**
     * Returns the token associated with a user.
     *
     * @param userDetails The user.
     * @return The token.
     */
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public User getUserFromToken(Token token) {
        return token.getUser();
    }

    /**
     * Returns the token associated user.
     *
     * @param token The token.
     * @return The user.
     */
    @Transactional(readOnly = true)
    public User getUserFromToken(String token) {
        Objects.requireNonNull(token);
        Token foundToken = tokenPersistenceService.findOne(token);

        if (foundToken != null) {
            return foundToken.getUser();
        }

        return null;
    }

    /**
     * Creates token for user.
     *
     * @param user The user.
     * @return The new token.
     */
    @Transactional(readOnly = false)
    public Token createUserToken(User user) {
        Objects.requireNonNull(user);
        int ttl = Integer.parseInt(env.getProperty("token.ttl"));
        Date expireAt = DateTime.now().plusSeconds(ttl).toDate();
        Token token = new Token(expireAt, user);
        tokenPersistenceService.delete(user.getToken());
        tokenPersistenceService.save(token);
        user.setToken(token);
        userPersistenceService.save(user);
        return token;
    }

}
