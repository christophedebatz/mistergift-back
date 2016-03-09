package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "file_metadata")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileMetadata
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    /**
     * Returns the file id.
     *
     * @return The file id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the file id.
     *
     * @param id The file id.
     */
    public void setId(long id) {
        this.id = id;
    }

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
