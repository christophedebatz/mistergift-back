package com.gvstave.mistergift.admin.auth.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.admin.response.ErrorResponse;
import com.gvstave.mistergift.admin.response.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class AuthenticationErrorHandler implements AuthenticationFailureHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenticationException exception) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        Response response = Response.withError(
                ErrorResponse.fromException(exception, HttpServletResponse.SC_FORBIDDEN)
        );

        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpResponse.getWriter().print(mapper.writeValueAsString(response));
    }
}
