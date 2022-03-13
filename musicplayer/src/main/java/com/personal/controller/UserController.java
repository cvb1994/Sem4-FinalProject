package com.personal.controller;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.config.AppProperties;
import com.personal.dto.ResetPasswordDto;
import com.personal.dto.UserDto;
import com.personal.serviceImp.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AppProperties appPropertis;
	
	
	@PostMapping()
	public ResponseEntity<?> createUser(@ModelAttribute UserDto model){
		return ResponseEntity.ok(userService.create(model));
	}
	
	@PutMapping()
	public ResponseEntity<?> updateUser(@ModelAttribute UserDto model){
		return ResponseEntity.ok(userService.update(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(userService.getAll());
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> getById(@PathVariable int userId){
		return ResponseEntity.ok(userService.getById(userId));
	}
	
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<?> deleteGenre(@PathVariable int userId){
		return ResponseEntity.ok(userService.delete(userId));
	}
	
	@GetMapping(value = "/password/resetlink")
	public ResponseEntity<?> linkResetPassword(@RequestParam("email") String email, HttpServletRequest request){
		return ResponseEntity.ok(userService.getLinkResetPassword(email));
	}
	
	@PostMapping(value = "/password/reset")
	public ResponseEntity<?> resetPassword(@ModelAttribute ResetPasswordDto model){
		return ResponseEntity.ok(userService.resetPassword(model));
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getPage(@ModelAttribute UserDto model){
		if(model.getPage() == 0) {
			model.setPage(appPropertis.getDefaultPage());
		}
		if(model.getSize() == 0) {
			model.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(userService.gets(model));
	}
}
