package com.personal.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.personal.config.AppProperties;
import com.personal.dto.SongDto;
import com.personal.serviceImp.SongService;

@RestController
@RequestMapping(value = "/api/song")
@CrossOrigin(origins = "*")
public class SongController {
	@Autowired
	SongService songSer;
	@Autowired
	private AppProperties appPropertis;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return ResponseEntity.ok(songSer.getAll());
	}
	
	@PostMapping(value = "/list")
	public ResponseEntity<?> getpage(@ModelAttribute SongDto model, Authentication authentication){
		if(model.getPage() == 0) {
			model.setPage(appPropertis.getDefaultPage());
		}
		if(model.getSize() == 0) {
			model.setSize(appPropertis.getDefaultPageSize());
		}
		return ResponseEntity.ok(songSer.gets(model, authentication));
	}
	
	@GetMapping(value = "/{songId}")
	public ResponseEntity<?> getById(@PathVariable int songId, Authentication authentication){
		return ResponseEntity.ok(songSer.getById(songId, authentication));
	}
	
	@GetMapping(value = "/count/{songId}")
	public ResponseEntity<?> increaseCount(@PathVariable int songId){
//		queueService.process(songId);
		songSer.increase(songId);
		return ResponseEntity.ok("da goi api");
	}
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> create(@ModelAttribute SongDto model) throws IOException{
		return ResponseEntity.ok(songSer.create(model));
	}
	
	@PutMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> update(@ModelAttribute SongDto model) throws IOException{
		return ResponseEntity.ok(songSer.update(model));
	}
	
	@DeleteMapping(value = "/{songId}")
	public ResponseEntity<?> delete(@PathVariable int songId){
		return ResponseEntity.ok(songSer.delete(songId));
	}
	
	@GetMapping(value = "/byAlbumId/{albumId}")
	public ResponseEntity<?> getSongsByAlbumId(@PathVariable int albumId, @RequestParam(name ="page" ,required = false) Integer page, @RequestParam(name = "size",required = false) Integer size, Authentication auth){
		SongDto criteria = new SongDto();
		criteria.setAlbumId(albumId);
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
		return ResponseEntity.ok(songSer.findSongByAlbumId(criteria, auth));
	}
	
	@GetMapping(value = "/byArtistId/{artistId}")
	public ResponseEntity<?> getSongsByArtistId(@PathVariable int artistId, @RequestParam(name ="page" ,required = false) Integer page, @RequestParam(name = "size",required = false) Integer size, Authentication auth){
		SongDto criteria = new SongDto();
		criteria.setArtistIds(Arrays.asList(artistId));
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
		return ResponseEntity.ok(songSer.findSongByArtistId(criteria, auth));
	}
	@GetMapping(value = "/byGenreId/{genreId}")
	public ResponseEntity<?> getSongsByGenreId(@PathVariable int genreId, @RequestParam(name ="page" ,required = false) Integer page, @RequestParam(name = "size",required = false) Integer size, Authentication auth){
		SongDto criteria = new SongDto();
		criteria.setGenreIds(Arrays.asList(genreId));
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
		return ResponseEntity.ok(songSer.findSongByGenreId(criteria, auth));
	}
	
}
