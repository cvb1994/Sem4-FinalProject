package com.personal.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SongDto extends BaseDto{
	public int id;
	public String title;					//required
	public String image;
	public String mediaUrl;
	public String timePlay;
	public String composer;
	public int listenCount;
	public List<ArtistDto> artists;
	public AlbumDto album;
	public List<GenreDto> genres;
	
	public List<Integer> artistIds;			//required
	public int albumId;						
	public List<Integer> genreIds;			//required
	
	public MultipartFile file;				//required
	public MultipartFile mp3;				//required
	
	
}
