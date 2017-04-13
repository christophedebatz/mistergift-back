package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.service.dto.GiftDto;
import com.gvstave.mistergift.data.service.dto.UserDto;


public class UserMapper
{
    /**
     *
     * @param user The user.
     * @return
     */
    public static UserDto map(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        dto.setKey(user.getExternalKey());
        return dto;
    }


}
