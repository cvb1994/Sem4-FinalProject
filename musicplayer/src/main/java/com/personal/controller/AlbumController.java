package com.personal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		return ResponseEntity.ok(albumSer.update(model));
	}
	
	@DeleteMapping(value = "/{albumId}")
	public ResponseEntity<?> delete(@PathVariable int albumId){
		return ResponseEntity.ok(albumSer.delete(albumId));
	}
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<?> getAllOrderByName(){
		return ResponseEntity.ok(albumSer.getAllOrderByName());
	}

	@GetMapping(value = "/byArtistId/{artistId}")
	public ResponseEntity<?> getAlbumByArtistId(@PathVariable int artistId, @RequestParam(name ="page" ,required = false) Integer page, @RequestParam(name = "size",required = false) Integer size){
		AlbumDto criteria = new AlbumDto();
		criteria.setArtistId(artistId);
		if(page == null) {
			criteria.setPage(appPropertis.getDefaultPage());
		}else{
			criteria.setPage(page);
		}
		if(size == null) {
			criteria.setSize(appPropertis.getDefaultPageSize());
		}else{
			criteria.setSize(size);
		}
		return ResponseEntity.ok(albumSer.finByArtistId(criteria));
	}
}
