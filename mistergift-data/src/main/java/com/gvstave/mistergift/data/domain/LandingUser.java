package com.gvstave.mistergift.data.domain;

import javax.persistence.*;

/**
 * The landing user (email actually).
 */
@Entity
@Table(schema = "mistergift", name = "landing_users")
public class LandingUser extends AbstractJpaEntity<Long>
{

    /** The landing user email. */
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    /**
     * Returns the landing user email.
     *
     * @return The email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the landing user email.
     *
     * @param email The email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
