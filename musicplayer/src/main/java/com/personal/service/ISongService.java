package com.personal.service;

import java.util.List;

import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.SongDto;


public interface ISongService {
	public List<SongDto> getAll();
	public PageDto gets(SongDto criteria);
	public SongDto getById(int songId);
	public SongDto getByName(String name);
	public ResponseDto save(SongDto model);
	public ResponseDto delete(int songId);
	public void increase(int songId);
}
