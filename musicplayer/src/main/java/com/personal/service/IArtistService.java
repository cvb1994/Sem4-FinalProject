package com.personal.service;

import java.util.List;

import com.personal.dto.ArtistDto;
import com.personal.dto.PageDto;

public interface IArtistService {
	public List<ArtistDto> getAll();
	public PageDto gets(ArtistDto criteria);
	public ArtistDto getById(int artistId);
	public ArtistDto getByName(String name);
	public ArtistDto save(ArtistDto model);
	public boolean delete(int artistId);
}
