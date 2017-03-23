package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Entity
@Table(schema = "mistergift", name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
public class User extends AbstractTimestampableJpaBaseEntity<Long> {

    /**
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
        ROLE_USER("ROLE_USER"),

        /**
         * An external invited user.
         */
        ROLE_EXTERNAL("ROLE_EXTERNAL");

        /** The name. */
        private final String name;

        /**
         * Constructor.
         *
         * @param name The name.
         */
        Role(String name) {
            this.name = name;
        }

        /**
         * Returns the name.
         *
         * @return The name.
         */
        public String getName() {
            return name;
        }

    }

    /** The invitation external key. */
    @Column(name = "external_key")
    private String externalKey;

    /** The user first name. */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** The user last name. */
    @Column(name = "last_name", nullable = false)
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
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private Token token;

    /** The user picture. */
    @OneToOne
    @JoinColumn(name = "picture_id")
    private FileMetadata picture;

    /** The user picture. */
    @OneToOne
    @JoinColumn(name = "thumbnail_id")
    private FileMetadata thumbnail;

    /** The user events. */
    @OneToMany
    @JoinTable(name = "user_events",
        joinColumns = { @JoinColumn(name = "user_id", updatable = false) }
    )
    private List<UserEvent> userEvents;

    @Column(name = "locale")
    private String locale;

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
    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user name.
     *
     * @return The user name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user name.
     *
     * @param firstName The user name.
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
     * Set the user first name.
     *
     * @param lastName The user first name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getExternalKey() {
        return externalKey;
    }

    /**
     *
     * @param externalKey
     */
    public void setExternalKey(String externalKey) {
        this.externalKey = externalKey;
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
     * Returns the user locale.
     *
     * @return The user locale.
     */
    public Locale getLocale () {
        return Optional.ofNullable(locale).map(Locale::new).orElse(Locale.ENGLISH);
    }

    /**
     * Sets the user locale.
     *
     * @param locale The user locale.
     */
    public void setLocale (Locale locale) {
        this.locale = locale.toString();
    }

    /**
     * Returns the user events.
     *
     * @return The user events.
     */
    @JsonIgnore
    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    /**
     * Sets the user events.
     *
     * @param userEvents The user events.
     */
    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

}
