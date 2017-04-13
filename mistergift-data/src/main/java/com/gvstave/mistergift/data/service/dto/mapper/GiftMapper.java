package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.domain.jpa.GiftComment;
import com.gvstave.mistergift.data.service.dto.CommentDto;
import com.gvstave.mistergift.data.service.dto.GiftDto;


public class GiftMapper
{
    /**
     *
     * @param gift The gift
     * @return
     */
    public static GiftDto map(Gift gift) {
        GiftDto dto = new GiftDto();
        dto.setEvent(gift.getEvent());
        dto.setProductId(gift.getProductId());
        dto.setUser(gift.getOwner());
        return dto;
    }


}
