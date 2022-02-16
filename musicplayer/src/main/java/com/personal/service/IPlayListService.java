package com.personal.service;

import java.util.List;

import com.personal.dto.PageDto;
import com.personal.dto.PlayListDto;
import com.personal.dto.ResponseDto;

public interface IPlayListService {
	public List<PlayListDto> findByUser(int userId);
	public PageDto gets(PlayListDto criteria);
	public PlayListDto getById(int id);
	public ResponseDto save(PlayListDto model);
	public ResponseDto delete(int id);
}
