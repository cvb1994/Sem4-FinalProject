package com.personal.service;

import org.springframework.security.core.Authentication;

import com.personal.dto.ResponseDto;
import com.personal.dto.SongDto;

import java.util.List;


public interface ISongService {
	public ResponseDto getAll();
	public ResponseDto gets(SongDto criteria, Authentication authentication);
	public ResponseDto getById(int songId, Authentication authentication);
	public ResponseDto create(SongDto model);
	public ResponseDto update(SongDto model);
	public ResponseDto delete(int songId);
	public void increase(int songId);
	
	public ResponseDto getTopSongByGenre(String genreName);
	List<SongDto> ListTrending(Authentication authentication);
	public ResponseDto findSongByAlbumId(SongDto criteria, Authentication authentication);
	public ResponseDto findSongByArtistId(SongDto criteria, Authentication authentication);
	public ResponseDto findSongByGenreId(SongDto criteria, Authentication authentication);
	public Long countSong();
	public void resetListTrending();
	public int countSongNewInMonth();
	public List<SongDto> newlySong();
	public List<SongDto> searchSong(String title, Authentication authentication);
	List<SongDto> ListTop15Trending(Authentication authentication);
}
