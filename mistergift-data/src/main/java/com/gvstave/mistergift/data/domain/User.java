package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends AbstractTimestampableJpaEntity<Long> {

    /** The user name. */
    @Column(name = "name", length = 255, nullable = false)
    private String name;

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

    /** The user events. */
    @OneToMany
    @JoinTable(name = "user_events",
            joinColumns = { @JoinColumn(name = "user_id", updatable = false) }
    )
    private List<UserEvent> userEvents;

    /** The user wish list. */
    @OneToMany
    @JoinTable(name = "user_products",
        joinColumns = { @JoinColumn(name = "user_id", updatable = false) }
    )
    private List<Product> wishList;

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
     * Returns the user name.
     *
     * @return The user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the user name.
     *
     * @param name The user name.
     */
    public void setName(String name) {
        this.name = name;
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
     *
     * @return
     */
    public List<Product> getWishList() {
        return wishList;
    }

    /**
     *
     * @param wishList
     */
    public void setWishList(List<Product> wishList) {
        this.wishList = wishList;
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
     *
     * @return
     */
    @JsonIgnore
    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    /**
     *
     * @param userEvents
     */
    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
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
