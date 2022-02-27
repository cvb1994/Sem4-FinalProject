package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.BaseDto;
import com.personal.entity.BaseEntity;

@Mapper(componentModel = "spring")
public interface BaseMapper {
	BaseDto entityToDto(BaseEntity base);
	BaseEntity dtoToEntity(BaseDto dto);
}
