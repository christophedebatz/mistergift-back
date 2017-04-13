package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.Participation;
import com.gvstave.mistergift.data.service.dto.ParticipationDto;


public class ParticipationMapper
{
    /**
     *
     * @param participation
     * @return
     */
    public static ParticipationDto toParticipationDto(Participation participation) {
        ParticipationDto dto = new ParticipationDto();
        dto.setGift(participation.getGift());
        dto.setUser(participation.getUser());
        dto.setEvent(participation.getEvent());
        dto.setType(participation.getType());
        return dto;
    }


}
