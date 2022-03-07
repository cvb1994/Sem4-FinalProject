package com.personal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.personal.dto.PlayListDto;
import com.personal.dto.SongDto;
import com.personal.dto.UserDto;
import com.personal.entity.PlayList;
import com.personal.entity.Song;
import com.personal.entity.User;

@Mapper(componentModel = "spring")
public interface PlayListMapper {
	PlayListDto entityToDto(PlayList playlist);
	PlayList dtoToEntity(PlayListDto dto);
	
//	@Mapping(target = "artists", expression = "java(null)")
	@Mapping(target = "album", expression = "java(null)")
	@Mapping(target = "genres", expression = "java(null)")
	SongDto songToSongDto(Song song);
	
}
