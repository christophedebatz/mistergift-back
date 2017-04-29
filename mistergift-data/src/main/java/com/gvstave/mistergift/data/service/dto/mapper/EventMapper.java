package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.service.dto.EventDto;
import com.gvstave.mistergift.data.service.dto.UserDto;


public class EventMapper
{
    /**
     *
     * @param event
     * @return
     */
    public static EventDto map(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setAddress(event.getAddress());
        dto.setCover(event.getCover());
        dto.setDescription(event.getDescription());
        dto.setEndDate(event.getEndDate());
        dto.setStartDate(event.getStartDate());
        dto.setName(event.getName());
        dto.setStatus(event.getStatus());
        return dto;
    }


}
