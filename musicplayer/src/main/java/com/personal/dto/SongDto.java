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
	private int id;
	private String title;					//required
	private String image;
	private String mediaUrl;
	private String timePlay;
	private String composer;
	private int listenCount;
	private List<ArtistDto> artists;
	private AlbumDto album;
	private List<GenreDto> genres;
	private Boolean vipOnly;
	
	private List<Integer> artistIds;			//required
	private int albumId;						
	private List<Integer> genreIds;			//required
	
	private MultipartFile file;				//required
	private MultipartFile mp3;				//required
	
	
}
