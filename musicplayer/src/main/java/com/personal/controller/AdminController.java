package com.personal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.dto.AdminDto;
import com.personal.serviceImp.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
	@Autowired
	private AdminService adminService;
    
	@PostMapping
	public ResponseEntity<?> createAdmin(@ModelAttribute AdminDto model){
		return ResponseEntity.ok(adminService.save(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<AdminDto> list = adminService.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{adminId}")
	public ResponseEntity<?> getById(@PathVariable int adminId){
		AdminDto model = adminService.getById(adminId);
		return ResponseEntity.ok(model);
	}
	
	@DeleteMapping(value = "/{adminId}")
	public ResponseEntity<?> deleteGenre(@PathVariable int adminId){
		return ResponseEntity.ok(adminService.delete(adminId));
	}

}
