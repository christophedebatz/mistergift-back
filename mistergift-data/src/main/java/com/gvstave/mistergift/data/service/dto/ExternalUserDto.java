package com.gvstave.mistergift.data.service.dto;

import com.gvstave.mistergift.data.domain.jpa.EventInvitation;
import com.gvstave.mistergift.data.domain.jpa.User;

import java.io.Serializable;
import java.util.Date;

public class ExternalUserDto implements Serializable {

    private EventInvitation.EventInvitationType type;

    private String email;

    private String firstName;

    private String lastName;

    private boolean admin;

    private Date invitationDate;

    public ExternalUserDto() {
        // for the players
    }

    public EventInvitation.EventInvitationType getType() {
        return type;
    }

    public void setType(EventInvitation.EventInvitationType type) {
        this.type = type;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(Date invitationDate) {
        this.invitationDate = invitationDate;
    }
}
