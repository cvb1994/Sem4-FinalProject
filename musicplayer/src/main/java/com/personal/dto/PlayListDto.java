package com.personal.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlayListDto extends BaseDto{
	public int id;
	public String name;						//required
	public UserDto user;
	public List<SongDto> songs;
	
	public int userId;						//required
	public List<Integer> listSongIds;		//required
}
