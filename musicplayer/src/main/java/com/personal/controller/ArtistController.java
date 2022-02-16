package com.personal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.personal.dto.ArtistDto;
import com.personal.serviceImp.ArtistService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/Artist")
public class ArtistController {
	@Autowired
	Utilities util;
	
	@Autowired
	ArtistService artistSer;
	
	@GetMapping
	public ResponseEntity<List<ArtistDto>> gelAll(){
		List<ArtistDto> list = artistSer.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{artistId}")
	public ResponseEntity<?> getById(@PathVariable int artistId){
		ArtistDto model = artistSer.getById(artistId);
		return ResponseEntity.ok(model);
	}
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> create(@ModelAttribute ArtistDto model) throws IOException{
		return ResponseEntity.ok(artistSer.save(model));
		
	}
	
	@DeleteMapping(value = "/{artistId}")
	public ResponseEntity<?> delete(@PathVariable int artistId){
		return ResponseEntity.ok(artistSer.delete(artistId));
	}
}
