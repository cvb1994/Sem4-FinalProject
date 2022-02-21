package com.personal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto{
	private int id;
	private String username;					//required
	private String password;					//required
	private String email;						//required
	private String phone;						//required
	private LocalDate birthday;
	private String firstName;
	private String lastName;
	private String gender;
	private String avatar;
	private LocalDate expireDate;
	private Boolean isVip;
	
//	private String userToken;
//	private LocalDateTime tokenExpire;
	
	private List<PlayListDto> playlists;
	private List<PaymentDto> payments;
	
	private MultipartFile file; 				//required
}
