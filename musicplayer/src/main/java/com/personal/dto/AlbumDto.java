package com.personal.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlbumDto extends BaseDto{
	public int id;
	public String name;						//required
	public String avatar;
	public String totalTime;
	public LocalDate releaseDate;
	public int totalListen;
	public ArtistDto artist;
	public List<SongDto> songs;
	
	public int artistId;					//required
	
	public MultipartFile file;				//required
	
	
}
