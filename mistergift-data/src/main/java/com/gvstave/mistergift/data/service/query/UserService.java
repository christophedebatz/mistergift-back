package com.gvstave.mistergift.data.service.query;


import com.gvstave.mistergift.data.domain.jpa.QUser;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.repositories.other.UserPersistenceService;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
@Service
public class UserService {

    /** The user repositories service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The password encoder. */
    @Inject
    private PasswordEncoder passwordEncoder;

    /**
     * Returns the user if found.
     *
     * @param email The user email.
     * @param rawPassword The user password.
     * @return The user if found, null else.
     */
    @Transactional(readOnly = true)
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
     * Returns the user from its id.
     *
     * @param id The user id.
     * @return The user.
     */
    @Transactional(readOnly = true)
    public Optional<User> fromId(Long id) {
        return Optional.ofNullable(userPersistenceService.findOne(id));
    }

	/**
     *
     * @param user
     * @return
     */
    public String getTimeline(User user) {
        Objects.requireNonNull(user);
        return null;
    }

}
