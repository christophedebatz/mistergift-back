package com.gvstave.mistergift.data.service.query.password;

/**
 * .
 */
public class UserIdNotFoundException extends Exception {

    /**
     *
     */
    public UserIdNotFoundException () {
        super("Unable to find user id in cache");
    }
}
