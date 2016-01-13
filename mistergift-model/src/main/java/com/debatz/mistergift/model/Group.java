package com.debatz.mistergift.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "user_group")
public class Group
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 75, nullable = false)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @OneToMany
    private List<User> administrators;

    /**
     * Returns the group id.
     *
     * @return The group id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the group id.
     *
     * @param id The group id.
     */
    public void setId(long id) {
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
    public List<User> getAdministrators() {
        return administrators;
    }

    /**
     * Set the group administrators.
     *
     * @param administrators The group administrators.
     */
    public void setAdministrators(List<User> administrators) {
        this.administrators = administrators;
    }
}
