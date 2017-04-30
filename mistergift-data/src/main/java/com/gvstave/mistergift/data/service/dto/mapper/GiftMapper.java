package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.service.dto.UserGiftDto;


public class GiftMapper
{
    /**
     *
     * @param gift The gift
     * @return
     */
    public static UserGiftDto map(Gift gift) {
        UserGiftDto dto = new UserGiftDto();
        dto.setEvent(gift.getEvent());
        dto.setProductId(gift.getProductId());
        dto.setUser(gift.getOwner());
        return dto;
    }


}
