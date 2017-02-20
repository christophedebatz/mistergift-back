package com.gvstave.mistergift.data.domain.jpa;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Represents a partner (like Amazon etc).
 */
@Entity
@Table(schema = "mistergift", name = "partners")
public class Partner extends AbstractJpaBaseEntity<Long> {

    /** The partner name. */
    @Column(name = "name", nullable = false)
    private String name;

    /** The partners credentials. */
    @Embedded
    private PartnerCredential credentials;

    public Partner () {
        // Hibernate constructor
    }

    /**
     *
     * @return
     */
    public String getName () {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public PartnerCredential getCredentials () {
        return credentials;
    }

    /**
     *
     * @param credentials
     */
    public void setCredentials (PartnerCredential credentials) {
        this.credentials = credentials;
    }

}
