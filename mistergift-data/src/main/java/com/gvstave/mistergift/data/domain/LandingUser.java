package com.gvstave.mistergift.data.domain;

import javax.persistence.*;
import java.util.Date;

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

    /** The create date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

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

    /**
     * Returns the gift creation date.
     *
     * @return The gift creation date.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Set the gift creation date.
     *
     * @param creationDate The gift creation date.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * {@inheritDoc}
     */
    @PrePersist
    public void persist() {
        setCreationDate(new Date());
    }

}
