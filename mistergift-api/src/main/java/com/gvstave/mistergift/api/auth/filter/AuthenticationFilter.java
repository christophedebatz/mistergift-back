package com.gvstave.mistergift.api.auth.filter;

import com.gvstave.mistergift.api.auth.handler.AuthenticationErrorHandler;
import com.gvstave.mistergift.api.auth.handler.AuthenticationSuccessHandler;
import com.gvstave.mistergift.data.domain.jpa.Token;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.persistence.jpa.service.UserPersistenceServiceJpa;
import com.gvstave.mistergift.data.service.query.UserService;
import com.gvstave.mistergift.data.service.command.UserWriterService;
import org.joda.time.DateTime;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * Filter the user login.
 */
@Service
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /** The user persistence service. */
    @Inject
    private UserService userService;

    /** The user writer service. */
    @Inject
    private UserWriterService userWriterService;

    /** The user persistence service. */
    @Inject
    private UserPersistenceServiceJpa userPersistenceService;

    /** The env. */
    @Inject
    private Environment environment;

    /**
     * Constructor
     *
     * @param defaultFilterProcessesUrl The default filter processes url.
     */

    public AuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));

        setAuthenticationFailureHandler(new AuthenticationErrorHandler());
        setAuthenticationSuccessHandler(new AuthenticationSuccessHandler());
    }

    /**
     * Try to identify the user that attempts a login.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    @Transactional
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            throw new AuthenticationCredentialsNotFoundException("Credentials not found.");
        }

        // try getting user from database
        User user = userService.fromCredentials(email, password);
        if (user == null) {
            throw new BadCredentialsException("Invalid credentials.");
        }

        // if user has a token, delete it
        if (user.getToken() != null) {
            userWriterService.removeToken(user);
        }

        // create token
        int ttl = Integer.parseInt(environment.getProperty("token.ttl"));
        Date expireAt = DateTime.now().plusSeconds(ttl).toDate();
        user.setToken(new Token(expireAt, user));
        user = userPersistenceService.save(user);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            user,
            user.getPassword(),
            Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().getName().toUpperCase())
            )
        );

        // set user details by the request
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // set the user locale
        LocaleContextHolder.setLocale(user.getLocale());

        return authenticationToken;
    }

}