package com.gvstave.mistergift.data.service;


import com.gvstave.mistergift.data.domain.QUser;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 *
 */
@Service
public class UserService {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

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

}
