package com.gvstave.mistergift.data.domain.jpa;

import com.querydsl.core.annotations.QueryEmbeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Represents partner credentials.
 */
@Embeddable
@QueryEmbeddable
public class PartnerCredential implements Serializable {

    /** The username. */
    @Column(name = "username", nullable = false)
    private String username;

    /** The password. */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Default constructor.
     */
    public PartnerCredential () {
        // useful for Hibernate
    }

    /**
     * Constructor.
     *
     * @param username The username.
     * @param password The password.
     */
    public PartnerCredential (String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username.
     *
     * @return The username.
     */
    public String getUsername () {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username.
     */
    public void setUsername (String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     *
     * @return The password.
     */
    public String getPassword () {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password.
     */
    public void setPassword (String password) {
        this.password = password;
    }

}
