package com.personal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.dto.GenreDto;
import com.personal.serviceImp.GenreService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/genre")
//@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {
	@Autowired
	GenreService genreSer;
	
	@Autowired
	Utilities util;
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> createGenre(@ModelAttribute GenreDto model){
		return ResponseEntity.ok(genreSer.save(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		List<GenreDto> list = genreSer.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{genreId}")
	public ResponseEntity<?> getById(@PathVariable int genreId){
		GenreDto model = genreSer.getById(genreId);
		return ResponseEntity.ok(model);
	}
	
	@DeleteMapping(value = "/{genreId}")
	public ResponseEntity<?> deleteGenre(@PathVariable int genreId){
		return ResponseEntity.ok(genreSer.delete(genreId));
	}
	
}
