package com.personal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.personal.dto.PlayListDto;
import com.personal.serviceImp.PlayListService;
import com.personal.utils.Utilities;

@RestController
@RequestMapping(value = "/api/playlist")
public class PlayListController {
	@Autowired
	Utilities util;
	
	@Autowired
	PlayListService playListSer;
	
	@GetMapping(value = "/list/{userId}")
	public ResponseEntity<?> getAllByUser(@PathVariable int userId){
		return ResponseEntity.ok(playListSer.findByUser(userId));
	}
	
	@PostMapping
	public ResponseEntity<?> create(@ModelAttribute PlayListDto model) throws IOException{
		return ResponseEntity.ok(playListSer.create(model));
	}
	
	@PutMapping
	public ResponseEntity<?> update(@ModelAttribute PlayListDto model) throws IOException{
		return ResponseEntity.ok(playListSer.update(model));
	}
	
	@GetMapping(value = "/{playListId}")
	public ResponseEntity<?> getById(@PathVariable int playListId){
		return ResponseEntity.ok(playListSer.getById(playListId));
	}
	
	@DeleteMapping(value = "/{playListId}")
	public ResponseEntity<?> deleteById(@PathVariable int playListId){
		return ResponseEntity.ok(playListSer.delete(playListId));
	}
	
	@PostMapping(value = "/like")
	public ResponseEntity<?> addRemoveFavoriteSong(@ModelAttribute PlayListDto model) throws IOException{
		return ResponseEntity.ok(playListSer.addRemoveToFavoritePlaylist(model));
	}
	
	@GetMapping(value = "/like/{userId}")
	public ResponseEntity<?> getFavoriteByUser(@PathVariable int userId){
		return ResponseEntity.ok(playListSer.getFavoritePlaylistUser(userId));
	}
	
	@GetMapping(value = "/like")
	public ResponseEntity<?> getFavoriteByUser(@RequestParam("userId") int userId, @RequestParam("songId") int songId){
		return ResponseEntity.ok(playListSer.checkFavoriteSong(userId, songId));
	}
}
