package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.BaseDto;
import com.personal.dto.GenreDto;
import com.personal.entity.BaseEntity;
import com.personal.entity.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {
	GenreDto entityToDto(Genre genre);
	Genre dtoToEntity(GenreDto dto);
	
	BaseDto entityToDto(BaseEntity base);
	BaseEntity dtoToEntity(BaseDto dto);
	
}
