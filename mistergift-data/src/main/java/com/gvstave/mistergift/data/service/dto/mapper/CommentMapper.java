package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.GiftComment;
import com.gvstave.mistergift.data.service.dto.CommentDto;

/**
 *
 */
public class CommentMapper
{
    /**
     *
     * @param giftComment The comment.
     * @return
     */
    public static CommentDto map(GiftComment giftComment) {
        CommentDto dto = new CommentDto();
        dto.setText(giftComment.getText());
        dto.setParentId(giftComment.getParentId());
        dto.setCreationDate(giftComment.getCreationDate());
        dto.setModificationDate(giftComment.getModificationDate());
        dto.setGift(GiftMapper.map(giftComment.getObject()));
        dto.setAuthor(UserMapper.map(giftComment.getAuthor()));
        return dto;
    }


}
