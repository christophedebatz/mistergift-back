package com.gvstave.mistergift.api.filter;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * .
 */
public class ContentLengthFilter implements Filter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, responseWrapper);
        responseWrapper.copyBodyToResponse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // do nothing here
    }

}