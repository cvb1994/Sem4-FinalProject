package com.personal.service;

import org.springframework.security.core.Authentication;

import com.personal.dto.ResponseDto;
import com.personal.dto.SongDto;


public interface ISongService {
	public ResponseDto getAll();
	public ResponseDto gets(SongDto criteria, Authentication authentication);
	public ResponseDto getById(int songId, Authentication authentication);
	public ResponseDto create(SongDto model);
	public ResponseDto update(SongDto model);
	public ResponseDto delete(int songId);
	public void increase(int songId);
	
	public ResponseDto getTopSongByGenre(String genreName);
}
