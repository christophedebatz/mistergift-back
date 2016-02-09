package com.gvstave.mistergift.admin.auth.service;

import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.domain.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    @Inject
    private UserPersistenceService userPersistenceService;

    /**
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = userPersistenceService.findByEmail(email);

        if (user == null || !encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong credentials.");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRoleName()));

        authentication = new UsernamePasswordAuthenticationToken(email, password, authorities);
        authentication.setAuthenticated(true);

        return authentication;
    }

    public boolean supports(Class<?> aClass) {
        return false;
    }
}
