package com.gvstave.mistergift.data.domain.jpa;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


/**
 * A comment.
 */
@MappedSuperclass
public class Comment<T extends Serializable, K extends Serializable> extends AbstractTimestampableJpaBaseEntity<K> {

    /** The author id. */
    @ManyToOne
    private User author;

    /** The commented object. */
    @ManyToOne
    private T object;

    /** The comment text. */
    private String text;

    /** The comment parent id. */
    private Long parentId;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
