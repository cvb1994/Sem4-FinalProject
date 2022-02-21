package com.personal.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.SongDto;


public interface ISongService {
	public List<SongDto> getAll();
	public PageDto gets(SongDto criteria, Authentication authentication);
	public SongDto getById(int songId, Authentication authentication);
	public ResponseDto save(SongDto model);
	public ResponseDto delete(int songId);
	public void increase(int songId);
	
	public List<SongDto> getTopSongByGenre(String genreName);
}
