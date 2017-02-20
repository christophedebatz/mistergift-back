package com.gvstave.mistergift.data.service.query.password;

/**
 * .
 */
public class PasswordTokenNotFound extends Exception {

    /**
     *
     */
    public PasswordTokenNotFound () {
        super("Unable to retrieve user password token");
    }
}
