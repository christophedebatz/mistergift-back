package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.xml.internal.rngom.parse.host.Base;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements BaseEntity<Long> {
    /** The user id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** The user first name. */
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    /** The user last name. */
    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    /** The user email. */
    @Column(name = "email", unique = true, length = 150, nullable = false)
    private String email;

    /** The user password. */
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    /** The user role. */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    /** The user token. */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "token_id", referencedColumnName = "id", nullable = true)
    private Token token;

    /** The user picture. */
    @OneToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private FileMetadata picture;

    /** The user picture. */
    @OneToOne
    @JoinColumn(name = "thumbnail_id", nullable = true)
    private FileMetadata thumbnail;

    /** The user groups. */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_groups", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
        @JoinColumn(name = "group_id", nullable = false, updatable = false) }
    )
    private List<Group> groups;

    /**
     *
     */
    public User() {
        this.groups = new ArrayList<>();
    }

    /**
     * Returns the entity id.
     *
     * @return the id.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Sets the entity id.
     *
     * @param id the id.
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user password.
     *
     * @return The user password.
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user password.
     *
     * @param password The user password.
     */
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
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
     * Returns the user role.
     *
     * @return The user role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set the user role.
     *
     * @param role The user role.
     */
    public void setRole(Role role) {
        this.role = role;
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
     * Returns the user profile thumbnail picture.
     *
     * @return The thumbnail picture.
     */
    public FileMetadata getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the user profile thumbnail picture.
     *
     * @param thumbnail The user thumbnail picture.
     */
    public void setThumbnail(FileMetadata thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Returns the user token.
     *
     * @return The user token.
     */
    @JsonIgnore
    public Token getToken() {
        return token;
    }

    /**
     * Sets the user token.
     *
     * @param token The user token.
     */
    public void setToken(Token token) {
        this.token = token;
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
    @JsonIgnore
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

    /*
     * Each user has a rank that defines its rights.
     */
    public enum Role {

        /**
         * An administrator.
         */
        ROLE_ADMIN("ROLE_ADMIN"),

        /**
         * A user.
         */
        ROLE_USER("ROLE_USER");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
