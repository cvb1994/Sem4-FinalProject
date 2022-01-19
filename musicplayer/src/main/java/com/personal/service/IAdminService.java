package com.personal.service;

import java.util.List;

import com.personal.dto.AdminDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;

public interface IAdminService {
	public List<AdminDto> getAll();
	public PageDto gets(AdminDto criteria);
	public AdminDto getById(int adminId);
	public AdminDto getByName(String name);
	public ResponseDto save(AdminDto model);
	public ResponseDto delete(int adminId);
}
