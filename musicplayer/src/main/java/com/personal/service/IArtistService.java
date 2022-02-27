package com.personal.service;

import com.personal.dto.ArtistDto;
import com.personal.dto.ResponseDto;

import java.util.List;

public interface IArtistService {
	public ResponseDto getAll();
	public ResponseDto gets(ArtistDto criteria);
	public ResponseDto getById(int artistId);
	public ResponseDto getByName(String name);
	public ResponseDto create(ArtistDto model);
	public ResponseDto update(ArtistDto model);
	public ResponseDto delete(int artistId);
	public List<ArtistDto> getTop10ByModifiedDateDesc();
}
