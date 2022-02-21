package com.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.dto.PaymentParamDto;
import com.personal.serviceImp.PaymentParamService;

@RestController
@RequestMapping("/api/paymentParam")
public class PaymentParamController {
	@Autowired
	private PaymentParamService paymentParamSer;
	
	@PostMapping
	public ResponseEntity<?> createParam(@ModelAttribute PaymentParamDto model){
		return ResponseEntity.ok(paymentParamSer.save(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(paymentParamSer.getAll());
	}
	
	@GetMapping(value = "/{paymentParamId}")
	public ResponseEntity<?> getById(@PathVariable int paymentParamId){
		return ResponseEntity.ok(paymentParamSer.getById(paymentParamId));
	}
	
	@DeleteMapping(value = "/{paymentParamId}")
	public ResponseEntity<?> deleteParam(@PathVariable int paymentParamId){
		return ResponseEntity.ok(paymentParamSer.delete(paymentParamId));
	}
}
