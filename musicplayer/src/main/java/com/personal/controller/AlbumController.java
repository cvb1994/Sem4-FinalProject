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
import com.personal.dto.AlbumDto;
import com.personal.serviceImp.AlbumService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/album")
@CrossOrigin(origins = "http://localhost:4200")
public class AlbumController {
	@Autowired
	Utilities util;
	@Autowired
	private AppProperties appPropertis;
	@Autowired
	AlbumService albumSer;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(albumSer.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getPage(@ModelAttribute AlbumDto model){
		if(model.getPage() == 0) {
			model.setPage(appPropertis.getDefaultPage());
		}
		if(model.getSize() == 0) {
			model.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(albumSer.gets(model));
	}
	
	@GetMapping(value = "/{albumId}")
	public ResponseEntity<?> getById(@PathVariable int albumId){
		return ResponseEntity.ok(albumSer.getById(albumId));
	}
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> create(@ModelAttribute AlbumDto model) throws IOException{
		return ResponseEntity.ok(albumSer.create(model));
	}
	
	@PutMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> update(@ModelAttribute AlbumDto model) throws IOException{
		return ResponseEntity.ok(albumSer.create(model));
	}
	
	@DeleteMapping(value = "/{albumId}")
	public ResponseEntity<?> delete(@PathVariable int albumId){
		return ResponseEntity.ok(albumSer.delete(albumId));
	}
}
