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

import com.personal.dto.AlbumDto;
import com.personal.serviceImp.AlbumService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/Album")
public class AlbumController {
	@Autowired
	Utilities util;
	
	@Autowired
	AlbumService albumSer;
	
	@GetMapping
	public ResponseEntity<List<AlbumDto>> getAll(){
		List<AlbumDto> list = albumSer.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{albumId}")
	public ResponseEntity<AlbumDto> getById(@PathVariable int albumId){
		AlbumDto model = albumSer.getById(albumId);
		return ResponseEntity.ok(model);
	}
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> create(@ModelAttribute AlbumDto model) throws IOException{
		return ResponseEntity.ok(albumSer.save(model));
	}
	
	@DeleteMapping(value = "/{albumId}")
	public ResponseEntity<?> delete(@PathVariable int albumId){
		return ResponseEntity.ok(albumSer.delete(albumId));
	}
}
