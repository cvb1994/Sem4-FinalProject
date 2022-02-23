package com.personal.service;

import com.personal.dto.ResponseDto;
import com.personal.dto.SystemParamDto;

public interface ISystemParamService {
	public ResponseDto getAll();
	public ResponseDto gets(SystemParamDto criteria);
	public ResponseDto getById(int id);
	public ResponseDto create(SystemParamDto dto);
	public ResponseDto update(SystemParamDto dto);
	public ResponseDto delete(int id);

}
