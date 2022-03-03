package com.personal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.personal.dto.PaymentDto;
import com.personal.dto.PlayListDto;
import com.personal.dto.UserDto;
import com.personal.entity.Payment;
import com.personal.entity.PlayList;
import com.personal.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserDto entityToDto(User user);
	User dtoToEntity(UserDto dto);
	
	@Mapping(target = "songs", expression = "java(null)")
//	@Mapping(target = "user", expression = "java(null)")
	PlayListDto playListToPlayListDto(PlayList playList);
	
	@Mapping(target = "user", expression = "java(null)")
	PaymentDto paymentToPaymentDto(Payment payment);
	
}
