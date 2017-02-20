package com.gvstave.mistergift.data.service.access;

import com.gvstave.mistergift.data.service.cache.CacheService;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.service.misc.ClientUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Controls and manages all API user access.
 */
@Service
public class UserAccessService {

    /** The maximum requests that an IP can make per second. */
    private static final Integer MAX_REQUESTS_PER_SECOND = 2;

    /** The cache service. */
    @Inject
    private CacheService cacheService;

    /**
     * Checks that user has the right to request the server to get resource.
     * If not, it throws an exception with the time to wait for the user.
     *
     * @param request The http request.
     * @throws TooManyRequestException If too many requests has been found.
     */
    public void checkTooManyRequests(HttpServletRequest request) throws TooManyRequestException {
        // check that the ip is not blocked
        String blockedKey = getCachePattern(request, "blocked");
        if (cacheService.exists(blockedKey)) {
            Long timeToWait = Math.round(cacheService.getTTL(blockedKey) * 1.1);
            cacheService.set(blockedKey, true, timeToWait);
            throw new TooManyRequestException(timeToWait);
        }

        Integer accessCount = getAccessesCount(request);

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
     */
    private Integer getAccessesCount (HttpServletRequest request) {
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
    private static String getCachePattern(HttpServletRequest request, String keySuffix) {
        if (keySuffix == null) {
            keySuffix = "";
        } else {
            keySuffix = ":" + keySuffix;
        }

        return String.format("access:ip:%s", ClientUtils.getClientAddr(request) + keySuffix);
    }

}
