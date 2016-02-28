package com.gvstave.mistergift.data.service;


import com.gvstave.mistergift.data.domain.QUser;
import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.TokenPersistenceService;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

/**
 *
 */
@Service
public class UserService {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The token persistence service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /** The password encoder. */
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor.
     *
     * @param passwordEncoder The password encoder.
     */
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Returns the user if found.
     *
     * @param email The user email.
     * @param rawPassword The user password.
     * @return The user if found, null else.
     */
    public User fromCredentials(String email, String rawPassword) {
        if (email == null || rawPassword == null) {
            return null;
        }

        BooleanExpression pUser = QUser.user.email.eq(email);
        User user = userPersistenceService.findOne(pUser);

        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            return user;
        }

        return null;
    }

    /**
     *
     * @param user
     */
    public void removeToken(User user) {
        Objects.requireNonNull(user);
        Token token = user.getToken();
        user.setToken(null);
        userPersistenceService.save(user);
        tokenPersistenceService.delete(token.getId());
    }

    /**
     * Saves a new user in database.
     *
     * @param user The new user.
     * @return The created user id.
     */
    public User saveOrUpdate(User user) {
        // encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set user role
        user.setRole(User.Role.ROLE_USER);

        // finally save
        return userPersistenceService.save(user);
    }

}
