package com.personal.service;

import com.personal.dto.AlbumDto;
import com.personal.dto.ResponseDto;

public interface IAlbumService {
	public ResponseDto getAll();
	public ResponseDto gets(AlbumDto criteria);
	public ResponseDto getById(int albumId);
	public ResponseDto getByName(String name);
	public ResponseDto create(AlbumDto model);
	public ResponseDto update(AlbumDto model);
	public ResponseDto delete(int albumId);
}
