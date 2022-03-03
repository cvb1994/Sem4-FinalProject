package com.personal.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayListDto extends BaseDto{
	private int id;
	private String name;						//required
//	private UserDto user;
	private List<SongDto> songs;
	
	private int userId;						//required
	private List<Integer> listSongIds;		//required
	private int songId;
}
