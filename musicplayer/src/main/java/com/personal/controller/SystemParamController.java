package com.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.config.AppProperties;
import com.personal.dto.SystemParamDto;
import com.personal.serviceImp.SystemParamService;

@RestController
@RequestMapping("/api/systemParam")
public class SystemParamController {
	@Autowired
	private SystemParamService systemParamSer;
	@Autowired
	private AppProperties appPropertis;
	
	@PostMapping
	public ResponseEntity<?> createParam(@ModelAttribute SystemParamDto model){
		return ResponseEntity.ok(systemParamSer.create(model));
	}
	
	@PutMapping
	public ResponseEntity<?> updateParam(@ModelAttribute SystemParamDto model){
		return ResponseEntity.ok(systemParamSer.update(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(systemParamSer.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getPage(@ModelAttribute SystemParamDto model){
		if(model.getPage() == 0) {
			model.setPage(appPropertis.getDefaultPage());
		}
		if(model.getSize() == 0) {
			model.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(systemParamSer.gets(model));
	}
	
	@GetMapping(value = "/{systemParamId}")
	public ResponseEntity<?> getById(@PathVariable int systemParamId){
		return ResponseEntity.ok(systemParamSer.getById(systemParamId));
	}
	
	@DeleteMapping(value = "/{systemParamId}")
	public ResponseEntity<?> deleteParam(@PathVariable int systemParamId){
		return ResponseEntity.ok(systemParamSer.delete(systemParamId));
	}
}
