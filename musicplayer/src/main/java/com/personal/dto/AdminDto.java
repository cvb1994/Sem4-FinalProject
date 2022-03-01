package com.personal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdminDto extends BaseDto{
	private int id;
	private String username;		//required
	private String password;		//required
	private String jwt;
	
}
