package com.gvstave.mistergift.api.auth.handler;

import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.TransactionScoped;
import java.util.Collections;
import java.util.List;

/**
 * Handler for http authentication.
 */
@Service
public class AuthenticationHandler implements AuthenticationManager, AuthenticationProvider {

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionScoped
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userPersistenceService.findByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("Bad credentials.");
        }

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(user.getRole().getName().toUpperCase())
        );

        return new UsernamePasswordAuthenticationToken(email, password, authorities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports (Class<?> aClass) {
        return false;
    }

}
