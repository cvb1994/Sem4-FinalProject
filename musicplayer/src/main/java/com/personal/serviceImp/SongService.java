package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.SongDto;
import com.personal.entity.Song;
import com.personal.mapper.SongMapper;
import com.personal.repository.ListenCountRepository;
import com.personal.repository.SongRepository;
import com.personal.service.ISongService;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class SongService implements ISongService{
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private SongMapper songMapper;
	@Autowired
	private ListenCountRepository listenCountRepo;
	@Autowired
	Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;
	
	private String serviceUrl = "http://localhost:8081/upload/img/";
	
	@Override
	public List<SongDto> getAll() {
		List<SongDto> list = songRepo.findAll().stream().map(songMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(s -> {
			s.setImage(serviceUrl.concat(s.getImage()));
		});
		return list;
	}
	
	@Override
	public PageDto gets(SongDto criteria) {
		Page<Song> page = songRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<SongDto> list = page.getContent().stream().map(songMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(s -> {
			s.setImage(serviceUrl.concat(s.getImage()));
		});
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		return pageDto;
	}

	@Override
	public SongDto getById(int songId) {
		SongDto song = songRepo.findById(songId).map(songMapper::entityToDto).orElse(null);
		if(song != null) {
			song.setImage(serviceUrl.concat(song.getImage()));
		}
		return song;
	}

	@Override
	public SongDto getByName(String name) {
		SongDto song = songRepo.findByTitle(name).map(songMapper::entityToDto).orElse(null);
		if(song != null) {
			song.setImage(serviceUrl.concat(song.getImage()));
		}
		return song;
	}

	@Override
	public ResponseDto save(SongDto model) {
		ResponseDto res = new ResponseDto();
		
		String fileType = util.getFileType(model.getFile());
		if(fileType != null) {
			String imageUrl = uploadDrive.uploadFile(model.getFile(), fileType);
			if(imageUrl == null) {
				res.setError("Lỗi trong quá trình upload file");
				res.setIsSuccess(false);
				return res;
			}
			model.setImage(imageUrl);
		} else {
			res.setError("File không hợp lệ");
			res.setIsSuccess(false);
			return res;
		}
		
		fileType = util.getFileType(model.getMp3());
		if(fileType != null) {
			String audioUrl = uploadDrive.uploadFile(model.getMp3(), fileType);
			if(audioUrl == null) {
				res.setError("Lỗi trong quá trình upload file");
				res.setIsSuccess(false);
				return res;
			}
			model.setMediaUrl(audioUrl);
		} else {
			res.setError("File không hợp lệ");
			res.setIsSuccess(false);
			return res;
		}
		
		Song song = Optional.ofNullable(model).map(songMapper::dtoToEntity).orElse(null);
		if (song != null) {
			songRepo.save(song);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo mới bài hát");
		res.setIsSuccess(false);
		return res;
		
	}

	@Override
	public ResponseDto delete(int songId) {
		ResponseDto res = new ResponseDto();
		Optional<Song> optSong = songRepo.findById(songId);
		if(optSong.isPresent()) {
			Song song = optSong.get();
			song.setDeleted(true);
			songRepo.save(song);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tìm thấy bài hát");
		res.setIsSuccess(false);
		return res;
	}
	
	@Override
	@Transactional
	public void increase(int songId) {
//		Optional<Song> optSong = songRepo.findById(songId);
//		if(optSong.isPresent()) {
//			LocalDate currentDate = LocalDate.now();
//			Optional<ListenCount> optlc = listenCountRepo.findBySongAndDate(optSong.get(), currentDate);
//			if(optlc.isPresent()) {
//				ListenCount lc = optlc.get();
//				lc.setCount(lc.getCount()+1);
//				listenCountRepo.save(lc);
//			} else {
//				ListenCount lc = new ListenCount();
//				lc.setCount(1);
//				lc.setDate(currentDate);
//				lc.setSong(optSong.get());
//				listenCountRepo.save(lc);
//			}
//		}
	}
}
