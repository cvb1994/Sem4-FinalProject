package com.personal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.dto.AlbumDto;
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
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<PlayListDto>> getAllByUser(@PathVariable int userId){
		List<PlayListDto> list = playListSer.findByUser(userId);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@ModelAttribute PlayListDto model) throws IOException{
		return ResponseEntity.ok(playListSer.save(model));
	}
}
