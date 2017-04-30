package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateDeserializer;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateSerializer;

import java.io.Serializable;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserGiftDto gift;

    private UserDto author;

    private String text;

    private Long parentId;

    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date creationDate;

    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date modificationDate;

    public CommentDto() {
        // this is for jackson
    }

    public UserGiftDto getGift() {
        return gift;
    }

    public void setGift(UserGiftDto gift) {
        this.gift = gift;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }
}
