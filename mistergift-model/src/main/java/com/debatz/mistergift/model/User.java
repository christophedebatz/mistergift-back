package com.debatz.mistergift.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private FileMetadata picture;

    @OneToMany
    private List<Group> groups;

    /**
     * Returns the user id.
     *
     * @return The user id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the user id.
     *
     * @param id The user id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the user first name.
     *
     * @return The user first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user first name.
     *
     * @param firstName The user first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the user last name.
     *
     * @return The user last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the user last name.
     *
     * @param lastName The user last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the user email.
     *
     * @return The user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user email.
     *
     * @param email The user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user profile picture.
     *
     * @return The user profile picture.
     */
    public FileMetadata getPicture() {
        return picture;
    }

    /**
     * Set the user profile picture.
     *
     * @param picture The user profile picture.
     */
    public void setPicture(FileMetadata picture) {
        this.picture = picture;
    }

    /**
     * Returns the user groups.
     *
     * @return The user groups.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Ste the user groups.
     *
     * @param groups The user groups.
     */
    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
