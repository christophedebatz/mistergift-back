package com.debatz.mistergift.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "gifts")
public class Gift
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 75, nullable = false)
    private String name;

    @Column(name = "brand", length = 75, nullable = true)
    private String brand;

    @Column(name = "reference", length = 25, nullable = true)
    private String reference;

    @Lob
    @Column(name = "description", length = 256, nullable = true)
    private String description;

    @OneToOne
    @JoinColumn(name = "picture_id", nullable = false)
    private FileMetadata picture;

    @ManyToMany
    private List<Group> groups;

    @Temporal(TemporalType.DATE)
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    /**
     * Returns the gift id.
     *
     * @return The gift id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the gift id.
     *
     * @param id The gift id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the gift name.
     *
     * @return The gift name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the gift name.
     *
     * @param name The gift name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the gift brand.
     *
     * @return The gift brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Set the gift brand.
     *
     * @param brand The gift brand.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the gift reference.
     *
     * @return The gift reference.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Set the gift reference.
     *
     * @param reference The gift reference.
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * Returns the gift description.
     *
     * @return The gift description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the gift description.
     *
     * @param description The gift description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the gift picture.
     *
     * @return The gift picture.
     */
    public FileMetadata getPicture() {
        return picture;
    }

    /**
     * Set the gift picture.
     *
     * @param picture The gift picture.
     */
    public void setPicture(FileMetadata picture) {
        this.picture = picture;
    }

    /**
     * Get the gift groups.
     *
     * @return The gift groups.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Set the gift groups.
     *
     * @param groups The gift groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
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
}
