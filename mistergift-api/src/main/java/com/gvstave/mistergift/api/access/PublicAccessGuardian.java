package com.gvstave.mistergift.api.access;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.lambdaworks.redis.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@Service
public class PublicAccessGuardian {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(PublicAccessGuardian.class);

    /** The public access service. */
    @Inject
    private PublicAccessService publicAccessService;

    /**
     *
     * @param httpRequest
     * @throws TooManyRequestException
     * @throws RedisException
     */
    public void check(HttpServletRequest httpRequest) throws TooManyRequestException, RedisException {
        Access access;

        try {
            access = publicAccessService.getClientAccess(httpRequest);
        } catch (Exception exception) {
            LOGGER.error("Redis error={}", exception);
            throw new RedisException(exception);
        }

        // if user cannot httpRequest anymore, throw
        if (access != null && access.getNextRequestAt().isAfterNow()) {
            String errorMessage = String.format("Too many requests. Try again at %s (after %s seconds).",
                    access.getNextRequestAt().toDateTime(),
                    Math.pow(2, access.getRequestCount())
            );

            throw new TooManyRequestException(errorMessage);
        }

        throw new RedisException("Unable to get access from redis server.");
    }

}
