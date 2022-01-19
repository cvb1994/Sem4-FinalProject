package com.personal.dto;

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
}
