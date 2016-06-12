package com.gvstave.mistergift.api.filter;

import com.gvstave.mistergift.api.access.UserAccessService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This filter checks all client request.
 */
public class UserAccessFilter implements Filter {

    /**
     * The user access service.
     */
    @Inject
    private UserAccessService userAccessService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no action
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            userAccessService.checkTooManyRequests((HttpServletRequest)request);
        } catch (Exception exception ) {
            throw new ServletException(exception.getMessage(), exception.getCause());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // no action
    }

}
