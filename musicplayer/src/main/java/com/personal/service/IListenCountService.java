package com.personal.service;

import com.personal.dto.ListenCountDto;
import com.personal.dto.ResponseDto;

public interface IListenCountService {
	public void saveTopTrendingBeforeReset();
	public ResponseDto gets(ListenCountDto criteria);
	public ResponseDto getById(int id);

}
