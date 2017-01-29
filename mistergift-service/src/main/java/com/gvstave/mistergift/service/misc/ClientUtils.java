package com.gvstave.mistergift.service.misc;

import javax.servlet.http.HttpServletRequest;

/**
 * .
 */
public class ClientUtils {

    /**
     * Returns the client IP address.
     *
     * @param request The servlet request.
     * @return The client IP.
     */
    public static String getClientAddr (HttpServletRequest request) {
        System.setProperty("java.net.preferIPv4Stack", "true");

        String ipAddress = request.getRemoteAddr();
        String forwardedRemoteAddress;

        if ((forwardedRemoteAddress = request.getHeader("X-FORWARDED-FOR")) != null) {
            ipAddress = forwardedRemoteAddress;
            int idx = ipAddress.indexOf(',');

            if (idx > -1) {
                ipAddress = ipAddress.substring(0, idx);
            }
        }

        // try getting header from nginx proxy pass
        if (ipAddress == null) {
            ipAddress = request.getHeader("X-Real-IP");
        }

        return ipAddress;
    }

}
