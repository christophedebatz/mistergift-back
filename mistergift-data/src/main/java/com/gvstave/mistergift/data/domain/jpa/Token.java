package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateDeserializer;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateSerializer;
import com.gvstave.mistergift.data.domain.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "mistergift", name = "tokens")
public class Token implements BaseEntity<String> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 75, nullable = false)
    private String id;

    @Column(name = "expire_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date expireAt;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    /**
     *
     */
    public Token() { }

    /**
     *
     * @param expireAt
     * @param user
     */
    public Token(Date expireAt, User user) {
        this.expireAt = expireAt;
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Date getExpireAt() {
        return expireAt;
    }

    /**
     *
     * @param expireAt
     */
    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public boolean isValid() {
        return expireAt.after(new Date());
    }

}
