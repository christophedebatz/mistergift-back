package com.gvstave.mistergift.service.mailing.exception;

/**
 * .
 */
public class MailException extends Exception {

    /**
     *
     * @param message
     */
    public MailException (String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public MailException (String message, Throwable cause) {
        super(message, cause);
    }
}
