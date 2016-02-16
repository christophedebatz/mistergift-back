package com.gvstave.mistergift.admin.auth.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Authentication authentication) throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof User)) {
            throw new RuntimeException("Could not find user in user details.");
        }

        User user = (User) authentication.getPrincipal();
        Token token = user.getToken();

        Map<String, Object> session = new LinkedHashMap<>();
        session.put("token", token.getValue());
        session.put("expireAt", token.getExpireAt());

        Map<String, Object> general = new LinkedHashMap<>();
        general.put("id", user.getId());
        general.put("email", user.getEmail());
        general.put("firstName", user.getFirstName());
        general.put("lastName", user.getLastName());
        general.put("session", session);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("payload", general);

        ObjectMapper mapper = new ObjectMapper();
        httpResponse.getWriter().print(mapper.writeValueAsString(response));

    }
}


