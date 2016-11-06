package com.gvstave.mistergift.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Global exception handler for filter chain.
 */
public class GlobalFilterExceptionHandler extends OncePerRequestFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            int status = HttpStatus.UNAUTHORIZED.value();
            response.getWriter().print(exception);

//            ErrorResponse errorResponse = ErrorResponse.fromException(exception, status);
//
//            ObjectMapper mapper = new ObjectMapper();
//            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//            response.setStatus(status);
//            response.getWriter().print(mapper.writeValueAsString(errorResponse));
        }

    }

}