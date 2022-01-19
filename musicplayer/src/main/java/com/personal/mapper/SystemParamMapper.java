package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.SystemParamDto;
import com.personal.entity.SystemParam;

@Mapper(componentModel = "spring")
public interface SystemParamMapper {
	SystemParamDto entityToDto(SystemParam sys);
	SystemParam dtoToEntity(SystemParamDto dto);
}
