package com.gvstave.mistergift.api.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter is in charge if public API access.
 */
public class CorsFilter implements Filter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no action here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("UserAccess-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("UserAccess-Control-Allow-Credentials", "true");
        response.setHeader("UserAccess-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("UserAccess-Control-Max-Age", "3600");
        response.setHeader("UserAccess-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");

        filterChain.doFilter(request, response);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // no action here
    }
}