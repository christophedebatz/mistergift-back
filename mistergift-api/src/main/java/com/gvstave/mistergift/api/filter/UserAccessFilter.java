package com.gvstave.mistergift.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.api.response.ErrorResponse;
import com.gvstave.mistergift.data.access.UserAccessService;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter checks all client request.
 */
@Service
public class UserAccessFilter implements Filter {

    /**
     * The user access service.
     */
    @Inject
    private UserAccessService userAccessService;

    /** The object mapper. */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            userAccessService.checkTooManyRequests((HttpServletRequest)request);
        } catch (TooManyRequestException exception) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            ErrorResponse errorResponse = ErrorResponse.fromException(exception, TooManyRequestException.getStatusCode());
            httpResponse.getWriter().print(mapper.writeValueAsString(errorResponse));
            httpResponse.setStatus(errorResponse.getStatus());
            httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpResponse.flushBuffer();
            return;
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
