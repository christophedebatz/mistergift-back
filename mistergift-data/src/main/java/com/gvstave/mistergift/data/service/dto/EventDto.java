package com.gvstave.mistergift.data.service.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateDeserializer;
import com.gvstave.mistergift.data.configuration.serialization.JacksonDateSerializer;
import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.FileMetadata;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private Long id;

    /** The event name. */
    @JsonProperty
    private String name;

    /** The event status. */
    @JsonProperty
    private Event.EventStatus status;

    /** The event description. */
    @JsonProperty
    private String description;

    /** The event start date. */
    @JsonProperty
    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date startDate;

    /** The event end date. */
    @JsonProperty
    @JsonDeserialize(using = JacksonDateDeserializer.class)
    @JsonSerialize(using = JacksonDateSerializer.class)
    private Date endDate;

    /** The event address. */
    @JsonProperty
    private String address;

    /** The event cover picture id. */
    @JsonProperty
    private FileMetadata cover;

    public EventDto() {
        // this is for the players
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event.EventStatus getStatus() {
        return status;
    }

    public void setStatus(Event.EventStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FileMetadata getCover() {
        return cover;
    }

    public void setCover(FileMetadata cover) {
        this.cover = cover;
    }
}
