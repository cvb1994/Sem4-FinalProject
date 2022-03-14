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
import com.personal.dto.GenreDto;
import com.personal.serviceImp.GenreService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/genre")
@CrossOrigin(origins = "*")
public class GenreController {
	@Autowired
	GenreService genreSer;
	@Autowired
	private AppProperties appPropertis;
	@Autowired
	Utilities util;
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> createGenre(@ModelAttribute GenreDto model){
		return ResponseEntity.ok(genreSer.create(model));
	}
	
	@PutMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> updateGenre(@ModelAttribute GenreDto model){
		return ResponseEntity.ok(genreSer.update(model));
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(genreSer.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getPage(@ModelAttribute GenreDto criteria){
		if(criteria.getPage() == 0) {
			criteria.setPage(appPropertis.getDefaultPage());
		}
		if(criteria.getSize() == 0) {
			criteria.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(genreSer.gets(criteria));
	}
	
	@GetMapping(value = "/{genreId}")
	public ResponseEntity<?> getById(@PathVariable int genreId){
		return ResponseEntity.ok(genreSer.getById(genreId));
	}
	
	@DeleteMapping(value = "/{genreId}")
	public ResponseEntity<?> deleteGenre(@PathVariable int genreId){
		return ResponseEntity.ok(genreSer.delete(genreId));
	}
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<?> getAllGenre(){
		return ResponseEntity.ok(genreSer.getAllOrderByName());
	}
	
}
