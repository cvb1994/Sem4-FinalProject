package com.personal.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.dto.PaymentDto;
import com.personal.serviceImp.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping()
	public ResponseEntity<?> create(@ModelAttribute PaymentDto model, HttpServletRequest req) throws IOException{
		return ResponseEntity.ok(paymentService.sendQuery(model, req));
	}
	
	@GetMapping(value = "/returnPay")
	public ResponseEntity<?> returnPayment(@RequestParam Map<String,String> allParams) throws IOException{
		return ResponseEntity.ok(paymentService.returnPayment(allParams));
	}
	
}
