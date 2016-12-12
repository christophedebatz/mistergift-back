package com.gvstave.mistergift.data.service.query.password;

/**
 * .
 */
public class UserNotFoundException extends Exception {

    /**
     *
     * @param userId
     */
    public UserNotFoundException (Long userId) {
        super("Unable to find user with id=" + userId);
    }

    /**
     *
     * @param userEmail
     */
    public UserNotFoundException (String userEmail) {
        super("Unable to find user with email=" + userEmail);
    }
}
