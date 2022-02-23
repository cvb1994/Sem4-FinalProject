package com.personal.dto;

import lombok.Data;

@Data
public class ResponseDto {
	private Boolean status;
	private String message;
	private Object content;
}
