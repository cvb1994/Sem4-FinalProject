package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.common.FileTypeEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.AlbumDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Album;
import com.personal.entity.Artist;
import com.personal.entity.SystemParam;
import com.personal.mapper.AlbumMapper;
import com.personal.repository.AlbumRepository;
import com.personal.repository.ArtistRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.service.IAlbumService;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class AlbumService implements IAlbumService {
	@Autowired
	private AlbumRepository albumRepo;
	@Autowired
	private ArtistRepository artistRepo;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private AlbumMapper albumMapper;
	@Autowired
	private Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;

	@Override
	public List<AlbumDto> getAll() {
		List<AlbumDto> list = albumRepo.findAll().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		
		return list;
	}

	@Override
	public PageDto gets(AlbumDto criteria) {
		Page<Album> page = albumRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<AlbumDto> list = page.getContent().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		
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
	public AlbumDto getById(int albumId) {
		AlbumDto album = albumRepo.findById(albumId).map(albumMapper::entityToDto).orElse(null);
		
		return album;
	}

	@Override
	public AlbumDto getByName(String name) {
		AlbumDto album = albumRepo.findByName(name).map(albumMapper::entityToDto).orElse(null);
		
		return album;
	}

	@Override
	public ResponseDto save(AlbumDto model) {
		ResponseDto res = new ResponseDto();
		Album album = Optional.ofNullable(model).map(albumMapper::dtoToEntity).orElse(null);
		if(album == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.ALBUM_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				album.setAvatar(optParam.get().getParamValue());
			} else {
				res.setError("Không tìm thấy ảnh đại diện mặc định");
				res.setIsSuccess(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.ALBUM_IMAGE.name, name);
				if(imageUrl == null) {
					res.setError("Lỗi trong quá trình upload file");
					res.setIsSuccess(false);
					return res;
				}
				album.setAvatar(imageUrl);
			} else {
				res.setError("File không hợp lệ");
				res.setIsSuccess(false);
				return res;
			}
		}
		
		Optional<Artist> artist = artistRepo.findById(model.getArtistId());
		if(!artist.isPresent()) return null;
		album.setArtist(artist.get());
		Album savedAlbum = albumRepo.save(album);
		if(savedAlbum != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo mới album");
		res.setIsSuccess(false);
		return res;
	}

	@Override
	public ResponseDto delete(int albumId) {
		ResponseDto res = new ResponseDto();
		Optional<Album> optAlbum = albumRepo.findById(albumId);
		if(optAlbum.isPresent()) {
			Album album = optAlbum.get();
			album.setDeleted(true);
			albumRepo.save(album);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tìm thấy album");
		res.setIsSuccess(false);
		return res;
	}

}
