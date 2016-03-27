package com.gvstave.mistergift.admin.access;

import com.gvstave.mistergift.data.redis.Redis;
import com.lambdaworks.redis.ScanCursor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
class PublicAccessService {

    /** The {@link Redis} instance. */
    @Inject
    private Redis redis;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    public Access getClientAccess(HttpServletRequest request) throws Exception {
        garbageClientAccess();

        String pattern = String.format("access:ip:%s", getRemoteAddr(request));
        Access access = (Access) redis.get(pattern);

        // if an ip access has been found, add time until next request
        if (access != null) {
            access.setRequestCount(access.getRequestCount() + 1);
            DateTime next = access
                    .getNextRequestAt()
                    .plusSeconds((int) Math.pow(2, access.getRequestCount()));

            access.setNextRequestAt(next);
        }

        // else, just creates an entry
        else {
            access = new Access();
            access.setRequestCount(0);
        }

        redis.set(pattern, access);
        return access;
    }

    /**
     *
     * @throws Exception
     */
    private void garbageClientAccess() throws Exception {
        ScanCursor cursor = new ScanCursor();
        cursor.setCursor("access:ip:*");

        List<String> keys = redis.getCommander().scan().getKeys();

        // for each ip key entry
        for (String key : keys) {
            Access access = (Access) redis.get(key);

            // remove all entries that authorize request by 1 hour in the past
            DateTime pivot = new DateTime().minusHours(1);
            if (access != null && access.getNextRequestAt().isBefore(pivot)) {
                redis.getCommander().del(key);
            }
        }
    }

    /**
     *
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String x;

        if ((x = request.getHeader("X-FORWARDED-FOR")) != null) {
            remoteAddr = x;
            int idx = remoteAddr.indexOf(',');

            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }
        return remoteAddr;

    }

}
