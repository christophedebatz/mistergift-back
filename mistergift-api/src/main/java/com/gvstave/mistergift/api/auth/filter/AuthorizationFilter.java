package com.gvstave.mistergift.api.auth.filter;

import com.gvstave.mistergift.api.auth.exception.InvalidTokenException;
import com.gvstave.mistergift.api.auth.exception.MissingTokenException;
import com.gvstave.mistergift.data.domain.jpa.Token;
import com.gvstave.mistergift.data.domain.jpa.TokenPersistenceService;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.service.query.TokenService;
import org.joda.time.DateTime;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Checks the header user token to maintains a stateless connection.
 */
@Service
public class AuthorizationFilter extends GenericFilterBean {

    /** The token service. */
    @Inject
    private TokenService tokenService;

    /** The token repositories service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /** The env. */
    @Inject
    private Environment environment;

    /** The request-scoped authenticated user. */
    private ThreadLocal<User> authenticatedUser;

    /** The unauthorized entry point. */
    private UnauthorizedEntryPoint entryPoint;

    /**
     * Default constructor.
     *
     * @param unauthorizedEntryPoint The unauthorized entry point.
     */
    public AuthorizationFilter(UnauthorizedEntryPoint unauthorizedEntryPoint) {
        this.entryPoint = unauthorizedEntryPoint;
    }

    /**
     * Checks if the request contains a valid token for current user.
     *
     * @param request The servlet request.
     * @param response The servlet response.
     * @param chain The chain object.
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // typecast both response and request
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // get the header token
        Optional<String> headerToken = Optional.ofNullable(
            httpRequest.getHeader(environment.getProperty("token.header.name"))
        );

        // reset the security context
        SecurityContextHolder.clearContext();

        // ensure that request contains a token
        if (headerToken.isPresent()) {
            String stringToken = headerToken.get();

            // ensure that this token is well-recognized
            Token token = tokenService.getToken(stringToken);
            if (token != null && token.isValid()) {
                User user = tokenService.getUserFromToken(token);
                UserDetails userDetails = getUserDetails(user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
                );

                // refresh token expiration date
                int expireAt = Integer.parseInt(environment.getProperty("token.ttl"));
                token.setExpireAt(DateTime.now().plusSeconds(expireAt).toDate());
                tokenPersistenceService.save(token);

                // set user details by the request
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                authenticatedUser.set(user);

                // continue the filters chain
                chain.doFilter(request, response);
            }
            else {
                entryPoint.commence(
                    httpRequest,
                    httpResponse,
                    new InvalidTokenException()
                );
            }
        }
        else {
            entryPoint.commence(
                httpRequest,
                httpResponse,
                new MissingTokenException()
            );
        }

    }

    /**
     *
     * @param user
     * @return
     */
    private UserDetails getUserDetails(User user) {
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.unmodifiableList(
                        Collections.singletonList(
                                new SimpleGrantedAuthority(user.getRole().getName().toUpperCase())
                        )
                )
            );
        }
        return null;
    }
}