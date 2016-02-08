package com.debatz.mistergift.data.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "users")
public class User {
    /**
     * The user id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The user first name.
     */
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    /**
     * The user last name.
     */
    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    /**
     * The user email.
     */
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    /**
     * The user password.
     */
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    /** The user role. */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    /** The user token. */
    @OneToOne
    @JoinColumn(name = "token_id")
    private Token token;

    /**
     * The user picture.
     */
    @OneToOne
    @JoinColumn(name = "picture_id", nullable = true)
    private FileMetadata picture;

    /**
     *
     */
    public User() {
        this.groups = new ArrayList<Group>();
    }

    /**
     * The user groups.
     */
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
     * Returns the user password.
     *
     * @return The user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user password.
     *
     * @param password The user password.
     */
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
     * Returns the user role as a string.
     *
     * @return The user role.
     */
    public String getRoleName() {
        return role.getName();
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
     * Returns the user token.
     *
     * @return The user token.
     */
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
        ADMIN("ROLE_ADMIN"),

        /**
         * A user.
         */
        USER("ROLE_ADMIN");

        private String name;

        Role(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
