package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.PaymentParamDto;
import com.personal.entity.PaymentParam;


@Mapper(componentModel = "spring")
public interface PaymentParamMapper {
	PaymentParamDto entityToDto(PaymentParam dto);
	PaymentParam dtoToEntity(PaymentParamDto dto);
}
