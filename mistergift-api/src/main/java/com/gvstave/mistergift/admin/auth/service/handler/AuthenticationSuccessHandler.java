package com.gvstave.mistergift.admin.auth.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gvstave.mistergift.admin.response.DataResponse;
import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!(authentication.getDetails() instanceof User)) {
            throw new RuntimeException("Could not fin user in user details.");
        }

        User user = (User) authentication.getDetails();

        Map<String, Object> userSession = new HashMap<>();
        userSession.put("email", user.getEmail());
        userSession.put("firstName", user.getFirstName());
        userSession.put("lastName", user.getLastName());
        userSession.put("id", user.getId());

        Map<String, Object> sessionInfo = new HashMap<>();
        Token token = user.getToken();
        sessionInfo.put("token", token.getValue());
        sessionInfo.put("expireAt", token.getExpirationDate());

        DataResponse dataResponse = new DataResponse(HttpServletResponse.SC_OK);
        dataResponse.addPayload(userSession);
        dataResponse.addPayload(sessionInfo);

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(mapper.writeValueAsString(dataResponse));

    }
}


