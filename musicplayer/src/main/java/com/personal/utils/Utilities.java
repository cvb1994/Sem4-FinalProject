package com.personal.utils;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.personal.common.FileExtensionEnum;
import com.personal.common.PaymentTimeUnit;

@Component
public class Utilities {
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
	
	public LocalDate getDateExpire(int time, String unit) {
		LocalDate current = LocalDate.now();
		LocalDate expire = null;
		if(PaymentTimeUnit.DAY.name.equalsIgnoreCase(unit)) {
			expire = current.plusDays(time);
		} else if(PaymentTimeUnit.MONTH.name.equalsIgnoreCase(unit)) {
			expire = current.plusMonths(time);
		} else if(PaymentTimeUnit.YEAR.name.equalsIgnoreCase(unit)) {
			expire = current.plusYears(time);
		} 
		return expire;
	}
}
