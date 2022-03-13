package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.ListenCountDto;
import com.personal.entity.ListenCount;

@Mapper(componentModel = "spring")
public interface ListenCountMapper {
	ListenCountDto entityToDto(ListenCount lc);
	
}
