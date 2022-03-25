package com.personal.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.personal.dto.PaymentDto;
import com.personal.dto.ResponseDto;

public interface IPaymentService {
	public ResponseDto getAll();
	public ResponseDto gets(PaymentDto criteria);
	public ResponseDto getById(int id);
	public ResponseDto sendQuery(PaymentDto model, HttpServletRequest req) throws UnsupportedEncodingException;
	public boolean returnPayment(@RequestParam Map<String,String> allParams);
	public int countPaymentNewInMonth();
	public ResponseDto profitInMonths(int month);
}
