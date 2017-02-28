package com.gvstave.mistergift.data.service.dto;

import com.gvstave.mistergift.data.domain.jpa.User;

import java.io.Serializable;
import java.util.Date;

public class ExternalUserDto implements Serializable {

    private User sender;

    private String email;

    private String name;

    private Date invitationDate;

    public ExternalUserDto(User sender, String email, String name) {
        this.sender = sender;
        this.email = email;
        this.name = name;
        this.invitationDate = new Date();
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Date invitationDate) {
        this.invitationDate = invitationDate;
    }
}
