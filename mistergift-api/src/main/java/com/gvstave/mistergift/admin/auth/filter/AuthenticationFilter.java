package com.gvstave.mistergift.admin.auth.filter;

import com.gvstave.mistergift.admin.auth.handler.AuthenticationErrorHandler;
import com.gvstave.mistergift.admin.auth.handler.AuthenticationSuccessHandler;
import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.UserPersistenceService;
import com.gvstave.mistergift.data.service.UserService;
import org.joda.time.DateTime;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
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

    /** The user persistence service. */
    @Inject
    private UserPersistenceService userPersistenceService;

    /** The environment/ */
    @Inject
    private Environment environment;

    /**
     * Constructor
     *
     * @param defaultFilterProcessesUrl The default filter processes url.
     */
    protected AuthenticationFilter(String defaultFilterProcessesUrl) {
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
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // ensure request method
        if (!request.getMethod().equalsIgnoreCase("post")) {
            throw new AuthenticationServiceException("Bad request method.");
        }

        // get credentials from request body
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

        // if user already has a token, replace it
        // todo

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

        return authenticationToken;
    }

}