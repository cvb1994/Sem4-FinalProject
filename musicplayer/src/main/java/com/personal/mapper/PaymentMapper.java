package com.personal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.personal.dto.PaymentDto;
import com.personal.dto.UserDto;
import com.personal.entity.Payment;
import com.personal.entity.User;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	PaymentDto entityToDto(Payment dto);
	Payment dtoToEntity(PaymentDto dto);
	
//	@Mapping(target = "playlists", expression = "java(null)")
//	@Mapping(target = "payments", expression = "java(null)")
//	UserDto userToUserDto(User user);
}
