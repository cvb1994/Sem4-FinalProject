package com.personal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
public class BaseDto {
	private int page;
	private int size;
}
