package com.personal.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.personal.dto.SongDto;
import com.personal.serviceImp.SongService;

@RestController
@RequestMapping(value = "/api/song")
public class SongController {
	@Autowired
	SongService songSer;
	
	private final String AUDIO_PATH = "E:\\Code\\Spring\\Personal Project\\musicplayer\\src\\main\\resources\\static\\upload\\mp3\\";
	public static final int BYTE_RANGE = 128;
	
//	private final QueueService queueService;
//
//	public SongController(QueueService queueService) {
//	    this.queueService = queueService;
//	}
	
	@GetMapping
	public ResponseEntity<List<SongDto>> getAll(){
		List<SongDto> list = songSer.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/{songId}")
	public ResponseEntity<SongDto> getById(@PathVariable int songId){
		SongDto model = songSer.getById(songId);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping(value = "/count/{songId}")
	public void increaseCount(@PathVariable int songId){
//		queueService.process(songId);
		songSer.increase(songId);
	}
	
	@PostMapping(consumes = {"multipart/form-data"})
	public ResponseEntity<?> create(@ModelAttribute SongDto model) throws IOException{
		return ResponseEntity.ok(songSer.save(model));
	}
	
	@DeleteMapping(value = "/{songId}")
	public ResponseEntity<?> delete(@PathVariable int songId){
		return ResponseEntity.ok(songSer.delete(songId));
	}
	
	@GetMapping(value = "audio/{fileName}")
	public ResponseEntity<byte[]> streamAudio(@RequestHeader(value = "Range", required = false) String httpRangeList,
			@PathVariable("fileName") String fileName){
		String url = "https://storage.googleapis.com/download/storage/v1/b/music-upload/o/D%E1%BA%A5u%20V%E1%BA%BFt_Wanbi%20Tu%E1%BA%A5n%20Anh_-1073929798.mp3?generation=1642426474841866&alt=media";
		long rangeStart = 0;
		long rangeEnd;
		byte[] data;
		long fileSize;
		String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
		
		try {
//			Path path = Paths.get(AUDIO_PATH, fileName);
//			Path path = Paths.get(url.concat(fileName));
			Path path = Paths.get(url);
			fileSize = sizeFromFile(path);
			if(httpRangeList == null) {
				return ResponseEntity.status(HttpStatus.OK)
		                 .header("Content-Type", "audio/" + fileType)
		                 .header("Content-Length", String.valueOf(fileSize))
		                 .body(readByteRange(AUDIO_PATH, fileName, rangeStart, fileSize - 1));
			}
			String[] ranges = httpRangeList.split("-");
			rangeStart = Long.parseLong(ranges[0].substring(6));
			if (ranges.length > 1) {
				rangeEnd = Long.parseLong(ranges[1]);
			} else {
				rangeEnd = fileSize - 1;
			}
			if(fileSize < rangeEnd) {
				rangeEnd = fileSize - 1;
			}
			data = readByteRange(AUDIO_PATH, fileName, rangeStart, rangeEnd);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.header("Content-Type", "audio/" + fileType)
				.header("Accept-Ranges", "bytes")
				.header("Content-Length", contentLength)
				.header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
				.body(data);
	}
	
	public byte[] readByteRange(String location, String filename, long start, long end) throws IOException {
	     Path path = Paths.get(location, filename);
	     try (InputStream inputStream = (Files.newInputStream(path));
	         ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
	        byte[] data = new byte[BYTE_RANGE];
	        int nRead;
	        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	           bufferedOutputStream.write(data, 0, nRead);
	        }
	        bufferedOutputStream.flush();
	        byte[] result = new byte[(int) (end - start) + 1];
	        System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
	        return result;
	     }
	  }
	
	private Long sizeFromFile(Path path) {
	     try {
	        return Files.size(path);
	     } catch (IOException ex) {
	        ex.printStackTrace();
	     }
	     return 0L;
	  }
	
	
}
