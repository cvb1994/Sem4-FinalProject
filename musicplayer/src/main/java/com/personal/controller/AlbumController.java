package com.personal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
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
//@CrossOrigin(origins = "http://localhost:4200")
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
		if(model == null) return new ResponseEntity("Upload Failed", HttpStatus.BAD_REQUEST);
		
		String fileName = util.copyFile(model.getFile());
		model.setAvatar(fileName);
		
		AlbumDto savedAlbum = albumSer.save(model);
		if(savedAlbum == null) return ResponseEntity.ok("Create Failed");
		return ResponseEntity.ok("Create Success");
	}
	
	@DeleteMapping(value = "/{albumId}")
	public ResponseEntity<?> delete(@PathVariable int albumId){
		boolean result = albumSer.delete(albumId);
		if(result) return ResponseEntity.ok("Delete Success");
		return ResponseEntity.ok("Delete Failed");
	}
}
