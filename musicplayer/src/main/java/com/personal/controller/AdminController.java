package com.personal.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.config.AppProperties;
import com.personal.dto.AdminDto;
import com.personal.serviceImp.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private AppProperties appPropertis;
    
	@PostMapping
	public ResponseEntity<?> createAdmin(@ModelAttribute AdminDto model){
		return ResponseEntity.ok(adminService.create(model));
	}
	
	@PutMapping
	public ResponseEntity<?> updateAdmin(@ModelAttribute AdminDto model){
		return ResponseEntity.ok(adminService.update(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(adminService.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getPage(@ModelAttribute AdminDto criteria){
		if(criteria.getPage() == 0) {
			criteria.setPage(appPropertis.getDefaultPage());
		}
		if(criteria.getSize() == 0) {
			criteria.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(adminService.gets(criteria));
	}
	
	@GetMapping(value = "/{adminId}")
	public ResponseEntity<?> getById(@PathVariable int adminId){
		return ResponseEntity.ok(adminService.getById(adminId));
	}
	
	@DeleteMapping(value = "/{adminId}")
	public ResponseEntity<?> deleteGenre(@PathVariable int adminId){
		return ResponseEntity.ok(adminService.delete(adminId));
	}

}
