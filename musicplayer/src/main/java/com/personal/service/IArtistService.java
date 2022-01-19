package com.personal.service;

import java.util.List;

import com.personal.dto.ArtistDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;

public interface IArtistService {
	public List<ArtistDto> getAll();
	public PageDto gets(ArtistDto criteria);
	public ArtistDto getById(int artistId);
	public ArtistDto getByName(String name);
	public ResponseDto save(ArtistDto model);
	public ResponseDto delete(int artistId);
}
