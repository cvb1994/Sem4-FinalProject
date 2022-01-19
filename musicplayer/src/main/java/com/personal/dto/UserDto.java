package com.personal.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends BaseDto{
	public int id;
	public String username;
	public String password;
	public String email;
	public String phone;
	public LocalDate birthday;
	public String firstName;
	public String lastName;
	public String gender;
	public String avatar;
	public LocalDate expireDate;
	
	public List<PlayListDto> playlists;
	public List<PaymentDto> payments;
	
	public MultipartFile file; 
}
