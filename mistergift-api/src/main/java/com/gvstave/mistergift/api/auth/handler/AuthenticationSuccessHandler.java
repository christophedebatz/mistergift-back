package com.gvstave.mistergift.api.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.data.domain.jpa.Token;
import com.gvstave.mistergift.data.domain.jpa.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Called when user has successfully login.
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

        User currentUser = (User) authentication.getPrincipal();
        Token token = currentUser.getToken();

        Map<String, Object> session = new LinkedHashMap<>();
        session.put("token", token.getId());
        session.put("expireAt", token.getExpireAt());

        Map<String, Object> general = new LinkedHashMap<>();
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("id", currentUser.getId());
        user.put("email", currentUser.getEmail());
        user.put("firstName", currentUser.getFirstName());
        user.put("lastName", currentUser.getLastName());
        general.put("user", user);
        general.put("session", session);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("payload", general);

        ObjectMapper mapper = new ObjectMapper();
        httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpResponse.setStatus(HttpStatus.OK.value());
        httpResponse.getWriter().print(mapper.writeValueAsString(response));

    }
}


