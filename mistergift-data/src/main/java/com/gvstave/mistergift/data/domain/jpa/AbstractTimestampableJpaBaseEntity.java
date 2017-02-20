package com.gvstave.mistergift.data.domain.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Super class for timestampable entities.
 *
 * @param <T> The primary key class type.
 */
@MappedSuperclass
abstract class AbstractTimestampableJpaBaseEntity<T extends Serializable> extends AbstractJpaBaseEntity<T>
{
    /** The create date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    /** The modification date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    /**
     * Default constructor.
     */
    public AbstractTimestampableJpaBaseEntity () {
        // this is for the player
    }

    /**
     * Constructor.
     *
     * @param creationDate      The creation date.
     * @param modificationDate  The modification date.
     */
    protected AbstractTimestampableJpaBaseEntity (Date creationDate, Date modificationDate) {
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
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
     * Returns the gift modification date.
     *
     * @return The gift modification date.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Set the gift modification date.
     *
     * @param modificationDate The gift modification date.
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * Called when entity is being persisted.
     */
    @PrePersist
    public void persist() {
        if (creationDate == null) {
            setCreationDate(new Date());
        }
        if (modificationDate == null) {
            setModificationDate(new Date());
        }
    }

    /**
     * Called when entity is being updated.
     */
    @PreUpdate
    public void update() {
        setModificationDate(new Date());
    }

}
