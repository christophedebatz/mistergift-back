package com.gvstave.mistergift.data.domain.mongo;

import com.gvstave.mistergift.data.domain.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * A comment.
 */
@Document(collection = "gifts.comments")
public class Comment implements BaseEntity<ObjectId> {

    /** The comment id. */
    @Id
    private ObjectId id;

    /** The comment parent id. */
    private String parentId;

    /** The object that is commented. */
    private Long targetId;

    /** The comment text. */
    private String text;

    /** The comment creation date. */
    private Date creationDate;

    /** The comment modification date. */
    private Date modificationDate;

    /** {@inheritDoc} */
    @Override
    public ObjectId getId () {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId (ObjectId id) {
        this.id = id;
    }

    /**
     * Getter for property 'parentId'.
     *
     * @return Value for property 'parentId'.
     */
    public String getParentId () {
        return parentId;
    }

    /**
     * Setter for property 'parentId'.
     *
     * @param parentId Value to set for property 'parentId'.
     */
    public void setParentId (String parentId) {
        this.parentId = parentId;
    }

    /**
     * Getter for property 'targetId'.
     *
     * @return Value for property 'targetId'.
     */
    public Long getTargetId () {
        return targetId;
    }

    /**
     * Setter for property 'targetId'.
     *
     * @param targetId Value to set for property 'targetId'.
     */
    public void setTargetId (Long targetId) {
        this.targetId = targetId;
    }

    /**
     * Getter for property 'text'.
     *
     * @return Value for property 'text'.
     */
    public String getText () {
        return text;
    }

    /**
     * Setter for property 'text'.
     *
     * @param text Value to set for property 'text'.
     */
    public void setText (String text) {
        this.text = text;
    }

    /**
     * Getter for property 'creationDate'.
     *
     * @return Value for property 'creationDate'.
     */
    public Date getCreationDate () {
        return creationDate;
    }

    /**
     * Setter for property 'creationDate'.
     *
     * @param creationDate Value to set for property 'creationDate'.
     */
    public void setCreationDate (Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Getter for property 'modificationDate'.
     *
     * @return Value for property 'modificationDate'.
     */
    public Date getModificationDate () {
        return modificationDate;
    }

    /**
     * Setter for property 'modificationDate'.
     *
     * @param modificationDate Value to set for property 'modificationDate'.
     */
    public void setModificationDate (Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
