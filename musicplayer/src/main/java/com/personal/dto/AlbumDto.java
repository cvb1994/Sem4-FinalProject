package com.personal.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AlbumDto extends BaseDto{
	private int id;
	private String name;						//required
	private String avatar;
	private String totalTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate releaseDate;
	
	private int totalListen;
	private ArtistDto artist;
	private List<SongDto> songs;
	
	private int artistId;					//required
	
	private MultipartFile file;				//required
	
	
}
