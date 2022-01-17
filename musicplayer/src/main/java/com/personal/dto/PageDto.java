package com.personal.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageDto extends BaseDto{
	@SuppressWarnings("rawtypes")
	private List content;
	private long totalElements;
	private int number;
	private int numberOfElements;
	private int totalPages;
	
	private boolean first;
	private boolean last;
}
