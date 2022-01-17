package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.PlayListDto;
import com.personal.dto.UserDto;
import com.personal.entity.PlayList;
import com.personal.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDto entityToDto(User user);
	User dtoToEntity(UserDto dto);
	
	PlayListDto playListToPlayListDto(PlayList playList);
}
