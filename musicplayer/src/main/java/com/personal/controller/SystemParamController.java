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

import com.personal.dto.SystemParamDto;
import com.personal.serviceImp.SystemParamService;

@RestController
@RequestMapping("/api/systemParam")
public class SystemParamController {
	@Autowired
	private SystemParamService systemParamSer;
	
	@PostMapping
	public ResponseEntity<?> createParam(@ModelAttribute SystemParamDto model){
		return ResponseEntity.ok(systemParamSer.save(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(systemParamSer.getAll());
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
