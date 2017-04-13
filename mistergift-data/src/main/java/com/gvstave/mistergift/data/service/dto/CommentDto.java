package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private GiftDto gift;

    private UserDto user;

    private String text;

    private Date creationDate;

    private Date modificationDate;

    public CommentDto() {
        // this is for jackson
    }

    public GiftDto getGift() {
        return gift;
    }

    public void setGift(GiftDto gift) {
        this.gift = gift;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
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
