package com.gvstave.mistergift.service.mailing;

import com.gvstave.mistergift.service.mailing.exception.MailException;

import java.util.Locale;
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
     * @param locale     The locale.
     */
    void send(String expeditor, final String[] recipients, final Map<String, Object> data, Locale locale)
        throws MailException;

}
