package com.personal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.personal.dto.AlbumDto;
import com.personal.dto.ArtistDto;
import com.personal.dto.GenreDto;
import com.personal.dto.SongDto;
import com.personal.entity.Album;
import com.personal.entity.Artist;
import com.personal.entity.Genre;
import com.personal.entity.Song;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
	// @Mapping(target = "artist", expression = "java(null)")
	// @Mapping(target = "songs", expression = "java(null)")
	AlbumDto entityToDto(Album album);
	Album dtoToEntity(AlbumDto dto);
	
	@Mapping(target = "listSong", expression = "java(null)")
	@Mapping(target = "listAlbum", expression = "java(null)")
	ArtistDto artistToArtistDto(Artist artist);
	
//	@Mapping(target = "artists", expression = "java(null)")
	@Mapping(target = "album", expression = "java(null)")
	@Mapping(target = "genres", expression = "java(null)")
	SongDto songToSongDto(Song song);
	
}
