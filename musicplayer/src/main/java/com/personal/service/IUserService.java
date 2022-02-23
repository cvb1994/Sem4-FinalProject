package com.personal.service;

import com.personal.dto.ResetPasswordDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.UserDto;

public interface IUserService {
	public ResponseDto getAll();
	public ResponseDto gets(UserDto criteria);
	public ResponseDto getById(int userId);
	public ResponseDto getByName(String name);
	public ResponseDto create(UserDto model);
	public ResponseDto update(UserDto model);
	public ResponseDto delete(int userId);
	public ResponseDto getLinkResetPassword(String email);
	public ResponseDto resetPassword(ResetPasswordDto model);
}
