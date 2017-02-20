package com.gvstave.mistergift.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.api.response.ErrorResponse;
import com.gvstave.mistergift.api.response.Response;
import com.gvstave.mistergift.data.service.access.UserAccessService;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
            sendException(exception, (HttpServletResponse) response);
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

    /**
     * Sends the errored response to the client.
     *
     * @param exception The exception.
     * @param httpResponse The http response.
     * @throws IOException If the response-sending has failed.
     */
    private static void sendException(TooManyRequestException exception, HttpServletResponse httpResponse) throws IOException {
        Objects.requireNonNull(exception);
        Objects.requireNonNull(httpResponse);

        ObjectMapper mapper = new ObjectMapper();

        // generates the error
        ErrorResponse errorResponse = ErrorResponse.fromException(exception, TooManyRequestException.getStatusCode());
        errorResponse.withDetail("waitingTime", exception.getTimeToWait());

        // creates response
        httpResponse.getWriter().print(mapper.writeValueAsString(Response.withError(errorResponse)));
        httpResponse.setStatus(errorResponse.getStatus());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.flushBuffer();
    }

}
