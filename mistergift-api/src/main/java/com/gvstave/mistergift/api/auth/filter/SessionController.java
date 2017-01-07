package com.gvstave.mistergift.api.auth.filter;

import com.gvstave.mistergift.api.controller.EventController;
import com.gvstave.mistergift.api.configuration.annotation.UserRestricted;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@UserRestricted
@RestController
@RequestMapping(path = "/session", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {

	/** The logger. */
	private static Logger LOGGER = LoggerFactory.getLogger(EventController.class);

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(method = RequestMethod.DELETE)
	public void logout() throws UnauthorizedOperationException {
		LOGGER.debug("SessionController > logout");
		// to be implemented

        SecurityContextHolder.clearContext();
	}

}
