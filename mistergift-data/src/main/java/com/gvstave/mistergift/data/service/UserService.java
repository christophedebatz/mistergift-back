package com.gvstave.mistergift.data.service;


import com.gvstave.mistergift.data.domain.QUser;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 *
 */
@Service
@Transactional
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

        // null case
        if (email == null || rawPassword == null) {
            return null;
        }

        // get encoded password
        String password = passwordEncoder.encode(rawPassword);

        // compute database expression
        BooleanExpression pUser = QUser.user.email.eq(email).and(QUser.user.password.eq(password));

        // search in database
        return userPersistenceService.findOne(pUser);
    }

}
