package com.personal.service;

import com.personal.dto.AdminDto;
import com.personal.dto.ResponseDto;

public interface IAdminService {
	public ResponseDto getAll();
	public ResponseDto gets(AdminDto criteria);
	public ResponseDto getById(int adminId);
	public ResponseDto create(AdminDto model);
	public ResponseDto update(AdminDto model);
	public ResponseDto delete(int adminId);
}
