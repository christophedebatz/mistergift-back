package com.gvstave.mistergift.admin.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.admin.response.ErrorResponse;
import com.gvstave.mistergift.admin.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * End point when user is not authorized.
 */
@Service
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    /**
     * {@inheritDoc}
     */
    public void commence(HttpServletRequest request, HttpServletResponse httpResponse, AuthenticationException authException)
            throws IOException, ServletException {

        int statusCode = HttpStatus.UNAUTHORIZED.value();

        Response response = Response.withError(
                ErrorResponse.fromException(authException, statusCode)
        );

        ObjectMapper mapper = new ObjectMapper();
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(statusCode);
        httpResponse.getWriter().print(mapper.writeValueAsString(response));
    }
}
