package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(schema = "mistergift", name = "files")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileMetadata extends AbstractJpaBaseEntity<Long>
{

    /** The file url. */
    @Column(name = "url", length = 255, nullable = false)
    private String url;

    /** The file owner. */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    /**
     * Returns the file url.
     *
     * @return The file url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the file url.
     *
     * @param url The file url.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the file owner.
     *
     * @return The file owner.
     */
    @JsonIgnore
    public User getOwner() {
        return owner;
    }

    /**
     * Set the file owner.
     *
     * @param owner The file owner.
     */
    @JsonProperty
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
