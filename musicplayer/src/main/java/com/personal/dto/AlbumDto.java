package com.personal.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlbumDto extends BaseDto{
	public int id;
	public String name;
	public String avatar;
	public String totalTime;
	public LocalDate releaseDate;
	public int totalListen;
	public ArtistDto artist;
	public List<SongDto> songs;
	
	public int artistId;
	
	public MultipartFile file;
	
	
}
