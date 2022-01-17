package com.personal.service;

import java.util.List;

import com.personal.dto.AlbumDto;
import com.personal.dto.PageDto;

public interface IAlbumService {
	public List<AlbumDto> getAll();
	public PageDto gets(AlbumDto criteria);
	public AlbumDto getById(int albumId);
	public AlbumDto getByName(String name);
	public AlbumDto save(AlbumDto model);
	public boolean delete(int albumId);
}
