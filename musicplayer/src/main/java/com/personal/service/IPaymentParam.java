package com.personal.service;

import java.util.List;

import com.personal.dto.PaymentParamDto;
import com.personal.dto.ResponseDto;

public interface IPaymentParam {
	public List<PaymentParamDto> getAll();
	public PaymentParamDto getById(int id);
	public ResponseDto save(PaymentParamDto model);
	public ResponseDto delete(int id);

}
