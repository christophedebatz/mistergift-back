package com.gvstave.mistergift.api.access;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.data.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Controls and manages all API user access.
 */
@Service
public class UserAccessService {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(UserAccessService.class);

    /** The maximum requests that an IP can make per second. */
    private static final Integer MAX_REQUESTS_PER_SECOND = 3;

    /** The cache service. */
    @Inject
    private CacheService cacheService;

    /**
     * Checks that user has the right to request the server to get resource.
     * If not, it throws an error announcing the time to wait for the user.
     *
     * @param request The http request.
     * @throws TooManyRequestException If too many requests has been found.
     * @throws Exception If there has been a cache exception.
     */
    public void checkTooManyRequests(HttpServletRequest request) throws Exception {
        // check that the ip is not blocked
        String blockedKey = getCachePattern(request, "blocked");
        if (cacheService.exists(blockedKey)) {
            Long timeToWait = cacheService.getTTL(blockedKey);
            throw new TooManyRequestException(timeToWait);
        }

        Integer accessCount;

        try {
            accessCount = getAccessCount(request);
        } catch (Exception exception) {
            LOGGER.error("Cache error={}", exception);
            throw exception;
        }

        // if user cannot request anymore, throw
        if (accessCount != null && accessCount > MAX_REQUESTS_PER_SECOND) {
            Long timeToWait = (long) 2 * accessCount;
            cacheService.set(getCachePattern(request, "blocked"), true, timeToWait);
            throw new TooManyRequestException(timeToWait);
        }
    }

    /**
     * Returns the accesses count of an IP.
     *
     * @param request The http request.
     * @return The number of request for the current IP address.
     * @throws Exception If there has been a cache exception.
     */
    private Integer getAccessCount(HttpServletRequest request) throws Exception {
        String key = getCachePattern(request, null);
        Object data = cacheService.get(key);

        Integer accessNumber = Optional.ofNullable(data)
                .map(Object::toString)
                .map(Integer::valueOf)
                .map(access -> access + 1)
                .orElse(1);

        cacheService.set(key, accessNumber, (long)1);
        return accessNumber;
    }

    /**
     * Returns the cache string pattern for user IP address count.
     *
     * @param request The http request.
     * @return The string pattern.
     */
    private String getCachePattern(HttpServletRequest request, String keySuffix) {
        if (keySuffix == null) {
            keySuffix = "";
        } else {
            keySuffix = ":" + keySuffix;
        }

        return String.format("access:ip:%s", getRemoteAddr(request) + keySuffix);
    }

    /**
     * Returns the remote IP address.
     *
     * @param request The http address.
     * @return The IP address.
     */
    private static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwardedRemoteAddress;

        if ((forwardedRemoteAddress = request.getHeader("X-FORWARDED-FOR")) != null) {
            remoteAddr = forwardedRemoteAddress;
            int idx = remoteAddr.indexOf(',');

            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }

        return remoteAddr;
    }

}
