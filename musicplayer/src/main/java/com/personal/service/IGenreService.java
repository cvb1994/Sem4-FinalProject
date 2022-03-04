package com.personal.service;

import com.personal.dto.GenreDto;
import com.personal.dto.ResponseDto;

public interface IGenreService {
	public ResponseDto getAll();
	public ResponseDto gets(GenreDto criteria);
	public ResponseDto getById(int genreId);
	public ResponseDto getByName(String name);
	public ResponseDto create(GenreDto model);
	public ResponseDto update(GenreDto model);
	public ResponseDto delete(int genreId);
	public ResponseDto getAllOrderByName();
	public Long countGenre();
}
