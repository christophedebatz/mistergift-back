package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.domain.jpa.UserPersistenceService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collections;

/**
 *
 */
@Service
public class UserProvider implements UserDetailsService {

    /** The user repositories service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userPersistenceService.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(
                    new SimpleGrantedAuthority(user.getRole().getName().toUpperCase())
                )
            );
        }

        return null;
    }

}
