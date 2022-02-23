package com.personal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.personal.config.AppProperties;
import com.personal.dto.ArtistDto;
import com.personal.serviceImp.ArtistService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/artist")
@CrossOrigin(origins = "http://localhost:4200")
public class ArtistController {
	@Autowired
	Utilities util;
	@Autowired
	private AppProperties appPropertis;
	@Autowired
	ArtistService artistSer;
	
	@GetMapping
	public ResponseEntity<?> gelAll(){
		return ResponseEntity.ok(artistSer.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> gelPage(@ModelAttribute ArtistDto model){
		if(model.getPage() == 0) {
			model.setPage(appPropertis.getDefaultPage());
		}
		if(model.getSize() == 0) {
			model.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(artistSer.gets(model));
	}
	
	@GetMapping(value = "/{artistId}")
	public ResponseEntity<?> getById(@PathVariable int artistId){
		return ResponseEntity.ok(artistSer.getById(artistId));
	}
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> create(@ModelAttribute ArtistDto model) throws IOException{
		return ResponseEntity.ok(artistSer.create(model));
	}
	
	@PutMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> update(@ModelAttribute ArtistDto model) throws IOException{
		return ResponseEntity.ok(artistSer.create(model));
	}
	
	@DeleteMapping(value = "/{artistId}")
	public ResponseEntity<?> delete(@PathVariable int artistId){
		return ResponseEntity.ok(artistSer.delete(artistId));
	}
}
