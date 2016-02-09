package com.gvstave.mistergift.data.domain;

import javax.persistence.*;

@Entity
@Table(schema = "mistergift", name = "links")
public class Link
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "gift_id", nullable = false)
    private Gift gift;

    @Column(name = "url", length = 255, nullable = false)
    private String url;

    /**
     * Returns the link id.
     *
     * @return The link id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the link id.
     *
     * @param id The link id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the linked gift.
     *
     * @return The linked gift.
     */
    public Gift getGift() {
        return gift;
    }

    /**
     * Set the linked gift.
     *
     * @param gift The linked gift.
     */
    public void setGift(Gift gift) {
        this.gift = gift;
    }

    /**
     * Returns the link url.
     *
     * @return The link url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the link url.
     *
     * @param url The link url.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
