package com.personal.service;

import com.personal.dto.AlbumDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Album;

import java.util.List;

public interface IAlbumService {
	public ResponseDto getAll();
	public ResponseDto gets(AlbumDto criteria);
	public ResponseDto getById(int albumId);
	public ResponseDto getByName(String name);
	public ResponseDto create(AlbumDto model);
	public ResponseDto update(AlbumDto model);
	public ResponseDto delete(int albumId);
	public List<AlbumDto> getTop5ByModifiedDateDesc();
	public List<AlbumDto> getTop10ByModifiedDateDesc();
	public ResponseDto getAllOrderByName();
	public ResponseDto finByArtistId(AlbumDto criteria);
	public Long coutAlbum();
}
