package com.personal.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ArtistDto extends BaseDto{
	public int id;
	public String name;
	public String avatar;
	public String gender;
	public LocalDate birthday;
	public String description;
	public String nationality;
	
	public List<SongDto> listSong;
	public List<AlbumDto> listAlbum;
	
	public MultipartFile file;
}
