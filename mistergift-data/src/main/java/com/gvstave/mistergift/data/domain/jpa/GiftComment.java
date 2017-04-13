package com.gvstave.mistergift.data.domain.jpa;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;


/**
 * A gift comment.
 */
@Entity
@Table(schema = "mistergift", name = "gift_comments")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect
public class GiftComment extends Comment<Gift, Long> {

    /**
     * Default constructor.
     * This is for Hibernate.
     */
    public GiftComment() {
        setModificationDate(new Date());
        setCreationDate(new Date());
    }
    
}
