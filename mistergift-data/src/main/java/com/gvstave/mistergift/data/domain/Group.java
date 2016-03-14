package com.gvstave.mistergift.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(schema = "mistergift", name = "group")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 75, unique = true, nullable = false)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "group_users", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false, updatable = false) }, inverseJoinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) }
    )
    private List<User> members;

    @ManyToMany
    @JoinTable(name = "group_admins", joinColumns = {
        @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = {
        @JoinColumn(name = "group_id", nullable = false, updatable = false) }
    )
    private List<User> administrators;

    /**
     * Constructor.
     */
    public Group() {
        this.members = new ArrayList<>();
        this.administrators = new ArrayList<>();
    }

    /**
     * Returns the group id.
     *
     * @return The group id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the group id.
     *
     * @param id The group id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the group name.
     *
     * @return The group name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the group name.
     *
     * @param name The group name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the group description.
     *
     * @return The group description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the group description.
     *
     * @param description The group description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the group administrators.
     *
     * @return The group administrators.
     */
    @JsonIgnore
    public List<User> getAdministrators() {
        return administrators;
    }

    /**
     * Set the group administrators.
     *
     * @param administrators The group administrators.
     */
    @JsonSetter
    public void setAdministrators(List<User> administrators) {
        this.administrators = administrators;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public List<User> getMembers() {
        return members;
    }

    /**
     *
     * @param members
     */
    @JsonSetter
    public void setMembers(List<User> members) {
        this.members = members;
    }
}
