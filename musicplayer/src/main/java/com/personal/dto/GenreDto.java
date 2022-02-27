package com.personal.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GenreDto extends BaseDto{
	private int id;
	private String name;
	private String avatar;
	
	private MultipartFile file;
}
