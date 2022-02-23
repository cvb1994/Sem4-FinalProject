package com.personal.service;

import com.personal.dto.PlayListDto;
import com.personal.dto.ResponseDto;

public interface IPlayListService {
	public ResponseDto findByUser(int userId);
	public ResponseDto gets(PlayListDto criteria);
	public ResponseDto getById(int id);
	public ResponseDto create(PlayListDto model);
	public ResponseDto update(PlayListDto model);
	public ResponseDto delete(int id);
}
