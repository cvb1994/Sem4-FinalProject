package com.personal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.personal.dto.ResetPasswordDto;
import com.personal.dto.UserDto;
import com.personal.serviceImp.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@PostMapping()
	public ResponseEntity<?> createUser(@ModelAttribute UserDto model){
		return ResponseEntity.ok(userService.save(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<UserDto> list = userService.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<?> getById(@PathVariable int userId){
		UserDto model = userService.getById(userId);
		return ResponseEntity.ok(model);
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
}
