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
public interface SongMapper {
	// @Mapping(target = "artists", expression = "java(null)")
	// @Mapping(target = "album", expression = "java(null)")
	// @Mapping(target = "genres", expression = "java(null)")
	SongDto entityToDto(Song song);
	Song dtoToEntity(SongDto dto);
	
	@Mapping(target = "listSong", expression = "java(null)")
	@Mapping(target = "listAlbum", expression = "java(null)")
	ArtistDto artistToArtistDto(Artist artist);
	
	GenreDto genreToGenreDto(Genre genre);
	
	@Mapping(target = "artist", expression = "java(null)")
	@Mapping(target = "songs", expression = "java(null)")
	AlbumDto albumToAlbumDto(Album album);
}
