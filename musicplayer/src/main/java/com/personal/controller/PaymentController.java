package com.personal.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.personal.config.AppProperties;
import com.personal.dto.PaymentDto;
import com.personal.serviceImp.PaymentService;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private AppProperties appPropertis;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(paymentService.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getpage(@ModelAttribute PaymentDto model){
		if(model.getPage() == 0) {
			model.setPage(appPropertis.getDefaultPage());
		}
		if(model.getSize() == 0) {
			model.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(paymentService.gets(model));
	}
	
	@PostMapping()
	public ResponseEntity<?> create(@ModelAttribute PaymentDto model, HttpServletRequest req) throws IOException{
		return ResponseEntity.ok(paymentService.sendQuery(model, req));
	}
	
	@GetMapping(value = "/returnPay")
	public ModelAndView  returnPayment(@RequestParam Map<String,String> allParams) throws IOException{
		boolean result = paymentService.returnPayment(allParams);
		if(result) {
			return new ModelAndView("redirect:" + "http://localhost:4200/returnPage");
		} else {
			return new ModelAndView("redirect:" + "http://localhost:4200/returnPageFailed");
		}
		
//		if(result) {
//			return new ModelAndView("redirect:" + "https://music-payment-ner3yu5pda-as.a.run.app/returnPage");
//		} else {
//			return new ModelAndView("redirect:" + "https://music-payment-ner3yu5pda-as.a.run.app/returnPageFailed");
//		}
		
	}
	
}
