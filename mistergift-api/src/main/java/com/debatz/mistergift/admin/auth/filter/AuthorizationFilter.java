package com.debatz.mistergift.admin.auth.filter;

import com.debatz.mistergift.admin.auth.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Filter the use session with token checking.
 */
public class AuthorizationFilter extends GenericFilterBean {

    /** The token service. */
    @Inject
    private TokenService tokenService;

    /**
     * Default constructor.
     */
    public AuthorizationFilter(AuthenticationManager auth) { }

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
        Map<String, String[]> parameters = request.getParameterMap();

        // ensure that request contains a token
        if (parameters.containsKey("X-AUTH")) {
            String token = parameters.get("X-AUTH")[0];

            // ensure that this token is well-recognized
            if (tokenService.isTokenValid(token)) {

                UserDetails user = tokenService.getUserFromToken(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()
                );

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