package com.personal.service;

import java.util.List;

import com.personal.dto.AlbumDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;

public interface IAlbumService {
	public List<AlbumDto> getAll();
	public PageDto gets(AlbumDto criteria);
	public AlbumDto getById(int albumId);
	public AlbumDto getByName(String name);
	public ResponseDto save(AlbumDto model);
	public ResponseDto delete(int albumId);
}
