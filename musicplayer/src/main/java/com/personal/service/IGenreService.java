package com.personal.service;

import java.util.List;

import com.personal.dto.GenreDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;

public interface IGenreService {
	public List<GenreDto> getAll();
	public PageDto gets(GenreDto criteria);
	public GenreDto getById(int genreId);
	public GenreDto getByName(String name);
	public ResponseDto save(GenreDto model);
	public ResponseDto delete(int genreId);
}
