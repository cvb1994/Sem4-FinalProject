package com.personal.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.personal.utils.CloudStorageUtils;
import com.personal.utils.UploadToDrive;

@RestController
public class testApi {
	@Autowired
	CloudStorageUtils cloudUtils;
	@Autowired
	UploadToDrive driveUtil;

	@GetMapping("/test")
	public String test() {
		return "success";
	}
	
	@GetMapping("/login")
	public String login() {
		return "success login";
	}
	
	@GetMapping("/testlogin")
	public String testlogin() {
		return "success";
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
		 return cloudUtils.uploadObject(file, file.getOriginalFilename());
	}
	
	@GetMapping("/grantPermission")
	public String grantPermission() {
		try {
			driveUtil.grantPermission();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success grant";
	}
	
}
