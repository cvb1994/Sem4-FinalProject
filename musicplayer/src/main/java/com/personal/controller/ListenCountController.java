package com.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.config.AppProperties;
import com.personal.dto.ListenCountDto;
import com.personal.serviceImp.ListenCountService;

@RestController
@RequestMapping(value = "/api/listen-count")
@CrossOrigin(origins = "*")
public class ListenCountController {
	@Autowired
	private ListenCountService listenCountSer;
	
	@Autowired
	private AppProperties appPropertis;
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> gets(ListenCountDto criteria){
		System.out.println(criteria.getMonth());
		System.out.println(criteria.getYear());
		if(criteria.getPage() == 0) {
			criteria.setPage(appPropertis.getDefaultPage());
		}
		if(criteria.getSize() == 0) {
			criteria.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(listenCountSer.gets(criteria));
	}
	
	@GetMapping(value = "{countId}")
	public ResponseEntity<?> get(@PathVariable int countId){
		return ResponseEntity.ok(listenCountSer.getById(countId));
	}
}
