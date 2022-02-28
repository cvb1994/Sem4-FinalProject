package com.personal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate birthday;
	
	private String firstName;
	private String lastName;
	private String gender;
	private String avatar;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate vipExpireDate;
	private Boolean isVip;
	
//	private String userToken;
//	private LocalDateTime tokenExpire;
	
	private List<PlayListDto> playlists;
	private List<PaymentDto> payments;
	private String jwtToken;
	
	private MultipartFile file; 				//required
}
