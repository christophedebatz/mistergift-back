package com.gvstave.mistergift.data.domain.es;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Represents a user action in the site.
 */
@Document(indexName = "metrics", type = "user_actions")
public class UserAction {

    enum UserActionType {

        /** About events... */
        CREATE_EVENT("event.create"),

        JOIN_EVENT("event.join"),


        /** About gifts... */
        BOOK_GIFT("gift.booked"),

        UNBOOK_GIFT("gift.unbooked"),


        /** About comments... */
        POST_COMMENT("comment.create");

        /** The action type. */
        String type;

        /**
         * Constructor.
         *
         * @param type The type.
         */
        UserActionType(String type) {
            this.type = type;
        }

        /**
         * Returns the type.
         *
         * @return The type.
         */
        public String getType() {
            return type;
        }
    }

    /** The user id. */
    @Field(type = FieldType.Long, index = FieldIndex.analyzed)
    private Long userId;

    @Field(type = FieldType.Long, index = FieldIndex.analyzed)
    private Long eventId;

    /** The type of user action. */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String type;

    /** The jsonified content of action (this is specific of each action). */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String content;

    /** The date of the action. */
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
    private Date date;

    /**
     * Constructor.
     *
     * @param userId The user id.
     * @param type The action type.
     */
    public UserAction (Long userId, UserActionType type) {
        this.userId = userId;
        this.type = type.getType();
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Date getDate () {
        return date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

}
