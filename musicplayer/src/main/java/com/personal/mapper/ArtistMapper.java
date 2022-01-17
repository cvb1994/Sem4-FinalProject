package com.personal.mapper;

import org.mapstruct.Mapper;

import com.personal.dto.AlbumDto;
import com.personal.dto.ArtistDto;
import com.personal.dto.SongDto;
import com.personal.entity.Album;
import com.personal.entity.Artist;
import com.personal.entity.Song;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
	ArtistDto entityToDto(Artist artist);
	Artist dtoToEntity(ArtistDto dto);
	
	SongDto songToSongDto(Song song);
	AlbumDto albumToAlbumDto(Album album);
}
