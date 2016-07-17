package com.gvstave.mistergift.data.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Super class for timestampable entities.
 *
 * @param <T> The primary key class type.
 */
@MappedSuperclass
abstract class AbstractTimestampableJpaEntity<T extends Serializable> extends AbstractJpaEntity<T>
{
    /** The create date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    /** The modification date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    /**
     * Default constructor.
     */
    public AbstractTimestampableJpaEntity() {
    }

    /**
     * Constructor.
     *
     * @param creationDate      The creation date.
     * @param modificationDate  The modification date.
     */
    protected AbstractTimestampableJpaEntity(Date creationDate, Date modificationDate) {
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
     * Called when entity is persisted or updated.
     */
    @PrePersist
    @PreUpdate
    public void update() {
        setCreationDate(new Date());
        setModificationDate(new Date());
    }

}