package com.gvstave.mistergift.admin.auth.filter;

import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.persistence.TokenPersistenceService;
import com.gvstave.mistergift.data.service.TokenService;
import org.joda.time.DateTime;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import java.io.IOException;
import java.util.Optional;

/**
 * Checks the header user token to maintains a stateless connection.
 */
@Service
public class AuthorizationFilter extends GenericFilterBean {

    /** The token service. */
    @Inject
    private TokenService tokenService;

    /** The token persistence service. */
    @Inject
    private TokenPersistenceService tokenPersistenceService;

    /** The environment. */
    @Inject
    private Environment environment;

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

        // typecast the default given request
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // get the header token
        Optional<String> headerToken = Optional.ofNullable(
                httpRequest.getHeader(environment.getProperty("token.header.name"))
        );

        // reset the current security context
        SecurityContextHolder.getContext().setAuthentication(null);

        // ensure that request contains a token
        if (headerToken.isPresent()) {
            String stringToken = headerToken.get();

            // ensure that this token is well-recognized
            Token token = tokenService.getToken(stringToken);
            if (token != null && token.isValid()) {
                UserDetails user = tokenService.getUserFromToken(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
                );

                // refresh token expiration date
                int expireAt = Integer.parseInt(environment.getProperty("token.ttl"));
                token.setExpireAt(DateTime.now().plusSeconds(expireAt).toDate());
                tokenPersistenceService.save(token);

                // set user details by the request
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // continue the filters chain
        chain.doFilter(request, response);

    }
}