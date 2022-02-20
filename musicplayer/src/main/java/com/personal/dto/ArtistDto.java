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
public class ArtistDto extends BaseDto{
	public int id;
	public String name;						//required
	public String avatar;
	public String gender;
	public LocalDate birthday;
	public String description;
	public String nationality;
	
	public List<SongDto> listSong;
	public List<AlbumDto> listAlbum;
	
	public MultipartFile file;				//required
}
