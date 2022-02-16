package com.personal.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.personal.dto.PaymentDto;

public interface IPaymentService {
	public String sendQuery(PaymentDto model, HttpServletRequest req) throws UnsupportedEncodingException;
	public String returnPayment(@RequestParam Map<String,String> allParams);
}
