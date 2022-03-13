package com.personal.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ListenCountDto extends BaseDto{
	private int id;
	private int week;
	private int month;
	private int year;
	
	List<SongSimplyDto> listSong;
}
