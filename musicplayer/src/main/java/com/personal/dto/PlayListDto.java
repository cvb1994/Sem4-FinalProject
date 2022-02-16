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
	public String name;
	public UserDto user;
	public List<SongDto> songs;
	
	public int userId;
	public List<Integer> listSongIds;
}
