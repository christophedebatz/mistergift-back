package com.gvstave.mistergift.data.domain.jpa;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

/**
 * The landing user (email actually).
 */
@Entity
@Table(schema = "mistergift", name = "landing_users")
public class LandingUser extends AbstractJpaBaseEntity<Long>
{

    /** The landing user email. */
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    /** The landing user country (gelocated by its ip). */
    @Column(name = "country", nullable = true)
    private String country;

    /** The landing user region (gelocated by its ip). */
    @Column(name = "region", nullable = true)
    private String region;

    /** The landing user city (gelocated by its ip). */
    @Column(name = "city", nullable = true)
    private String city;

    /** The landing user IP address. */
    @Column(name = "ip", nullable = true)
    private String ip;

    /** The create date. */
    @Temporal(TemporalType.TIMESTAMP)
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
     * Returns the city.
     *
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city The city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
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
