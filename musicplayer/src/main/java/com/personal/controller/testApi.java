package com.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.personal.utils.CloudStorageUtils;

@RestController
public class testApi {
	@Autowired
	CloudStorageUtils cloudUtils;

	@GetMapping("/test")
	public String test() {
		return "seccess";
	}
	
	@GetMapping("/testCloud")
	public String testCloud() {
		String bucket = cloudUtils.findBucket("music-upload").getName();
		if(bucket != null) {
			return bucket;
		}
		
		return null;
	}
	
	@PostMapping(value = "/testObject", consumes = {"multipart/form-data"})
	public String testObject(@RequestPart("file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		 return cloudUtils.uploadObject(file);
	}
}
