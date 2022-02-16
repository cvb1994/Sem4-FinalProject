package com.personal.service;

import java.util.List;

import com.personal.dto.ResponseDto;
import com.personal.dto.SystemParamDto;

public interface ISystemParamService {
	public List<SystemParamDto> getAll();
	public SystemParamDto getById(int id);
	public ResponseDto save(SystemParamDto dto);
	public ResponseDto delete(int id);

}
