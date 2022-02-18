package com.personal.service;

import java.util.List;

import com.personal.dto.PageDto;
import com.personal.dto.ResetPasswordDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.UserDto;

public interface IUserService {
	public List<UserDto> getAll();
	public PageDto gets(UserDto criteria);
	public UserDto getById(int userId);
	public UserDto getByName(String name);
	public ResponseDto save(UserDto model);
	public ResponseDto delete(int userId);
	public ResponseDto getLinkResetPassword(String email);
	public ResponseDto resetPassword(ResetPasswordDto model);
}
