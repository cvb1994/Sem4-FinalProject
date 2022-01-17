package com.personal.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.personal.common.FileTypeEnum;

@Component
public class Utilities {
	private final String rootPathImg = "E:\\Code\\Spring\\Personal Project\\musicplayer\\src\\main\\resources\\static\\upload\\img\\";
	private final String rootPathMp3 = "E:\\Code\\Spring\\Personal Project\\musicplayer\\src\\main\\resources\\static\\upload\\mp3\\";
	
	public String getCurrentDateTime() {
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		return currentTime;
	}
	
	public String copyFile(MultipartFile file) throws IOException{
		if(file != null) {
			byte[] bytes = file.getBytes();
	        Path path = Paths.get(rootPathImg + file.getOriginalFilename());
	        Files.write(path, bytes);
	        return file.getOriginalFilename();
		}
		return "default_img.jpg";
		
	}
	
	public void deleteFile(String fileName) {
		String filePath = rootPathImg.concat(fileName);
		Path path = Paths.get(filePath);
		try {
			Files.deleteIfExists(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String copyFileMp3(MultipartFile file) throws IOException{
		byte[] bytes = file.getBytes();
        Path path = Paths.get(rootPathMp3 + file.getOriginalFilename());
        Files.write(path, bytes);
        return file.getOriginalFilename();
	}
	
	public String getFileType(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
		if(fileExtension.equalsIgnoreCase("mp3")) {
			return FileTypeEnum.AUDIO.name;
		} else if(fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("jpg")) {
			return FileTypeEnum.IMAGE.name;
		}
		return null;
	}
}
