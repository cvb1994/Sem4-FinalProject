package com.personal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdminDto extends BaseDto{
	public int id;
	public String username;		//required
	public String password;		//required
}
