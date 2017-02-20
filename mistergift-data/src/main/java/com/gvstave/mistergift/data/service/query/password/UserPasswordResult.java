package com.gvstave.mistergift.data.service.query.password;

/**
 * .
 */
public class UserPasswordResult {

    private String errorType;

    private boolean errored = false;

    public UserPasswordResult () {
    }

    public String getErrorType () {
        return errorType;
    }

    public void setErrorType (String errorType) {
        this.errorType = errorType;
        errored = true;
    }

    public boolean isErrored () {
        return errored;
    }

    public void setErrored (boolean errored) {
        this.errored = errored;
    }
}

