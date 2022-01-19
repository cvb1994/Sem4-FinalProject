package com.personal.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.personal.common.FileExtensionEnum;

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
	
	public String getTimeMilisecond() {
		Date current = new Date();
		long time = current.getTime();
		return String.valueOf(time);
	}
	
//	public String copyFile(MultipartFile file) throws IOException{
//		if(file != null) {
//			byte[] bytes = file.getBytes();
//	        Path path = Paths.get(rootPathImg + file.getOriginalFilename());
//	        Files.write(path, bytes);
//	        return file.getOriginalFilename();
//		}
//		return "default_img.jpg";
//		
//	}
//	
//	public void deleteFile(String fileName) {
//		String filePath = rootPathImg.concat(fileName);
//		Path path = Paths.get(filePath);
//		try {
//			Files.deleteIfExists(path);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public String copyFileMp3(MultipartFile file) throws IOException{
//		byte[] bytes = file.getBytes();
//        Path path = Paths.get(rootPathMp3 + file.getOriginalFilename());
//        Files.write(path, bytes);
//        return file.getOriginalFilename();
//	}
	
	public String getFileExtension(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
		if(fileExtension.equalsIgnoreCase("mp3")) {
			return FileExtensionEnum.AUDIO.name;
		} else if(fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("jpg")) {
			return FileExtensionEnum.IMAGE.name;
		}
		return null;
	}
	
	public String stringToNormalize(String str) {
		try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
      } catch (Exception ex) {
            ex.printStackTrace();
      }
      return null;
	}
	
	public String nameIdentifier(String name, String extensionType) {
		if(extensionType.equals(FileExtensionEnum.AUDIO.name)) {
			return stringToNormalize(name).toLowerCase().replace(" ", "").concat("_audio-file").concat(getTimeMilisecond());
		} else if(extensionType.equals(FileExtensionEnum.IMAGE.name)) {
			return stringToNormalize(name).toLowerCase().replace(" ", "").concat("_image_cover").concat(getTimeMilisecond());
		}
		return null;
	}
}
