package com.gvstave.mistergift.service.mailing;

import java.util.Map;

/**
 * Something that we can send by email.
 */
public interface Mailable {

    /**
     * Sends an email to the expeditor.
     *
     * @param expeditor  The expeditor.
     * @param recipients The recipients.
     * @param data       The data.
     */
    void send(String expeditor, final String[] recipients, final Map<String, Object> data);

}
