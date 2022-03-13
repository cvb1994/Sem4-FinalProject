package com.personal.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SystemParamDto extends BaseDto{
	private int id;
	private String paramName;
	private String paramValue;
	private MultipartFile file; 
}
