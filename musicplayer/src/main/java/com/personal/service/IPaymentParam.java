package com.personal.service;

import com.personal.dto.PaymentParamDto;
import com.personal.dto.ResponseDto;

public interface IPaymentParam {
	public ResponseDto getAll();
	public ResponseDto gets(PaymentParamDto criteria);
	public ResponseDto getById(int id);
	public ResponseDto create(PaymentParamDto model);
	public ResponseDto update(PaymentParamDto model);
	public ResponseDto delete(int id);

}
