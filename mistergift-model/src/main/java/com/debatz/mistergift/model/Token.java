package com.debatz.mistergift.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "mistergift", name = "user_group")
public class Token
{
    @Id
    @Column(name = "value", length = 75, nullable = false)
    private String value;

    @Column(name = "expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @OneToOne(mappedBy = "token")
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     *
     * @param expirationDate
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
        return expirationDate.after(new Date());
    }
}
