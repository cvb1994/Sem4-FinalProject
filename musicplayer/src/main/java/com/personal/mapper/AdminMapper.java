package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.AdminDto;
import com.personal.entity.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {
	AdminDto entityToDto(Admin admin);
	Admin dtoToEntity(AdminDto dto);
}
