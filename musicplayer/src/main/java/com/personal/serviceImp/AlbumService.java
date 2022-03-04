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
import com.personal.musicplayer.specification.AlbumSpecification;
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
	private AlbumSpecification albumSpec;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private AlbumMapper albumMapper;
	@Autowired
	private Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<AlbumDto> list = albumRepo.findAll().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto gets(AlbumDto criteria) {
		ResponseDto res = new ResponseDto();
		
		Page<Album> page = albumRepo.findAll(albumSpec.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<AlbumDto> list = page.getContent().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		if(page.getNumber() == 0) {
			pageDto.setFirst(true);
		}
		if(page.getNumber() == page.getTotalPages()-1) {
			pageDto.setLast(true);
		}
		res.setStatus(true);
		res.setContent(pageDto);
		return res;
	}

	@Override
	public ResponseDto getById(int albumId) {
		ResponseDto res = new ResponseDto();
		AlbumDto album = albumRepo.findById(albumId).map(albumMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(album);
		return res;
	}

	@Override
	public ResponseDto getByName(String name) {
		ResponseDto res = new ResponseDto();
		AlbumDto album = albumRepo.findByName(name).map(albumMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(album);
		return res;
	}

	@Override
	public ResponseDto create(AlbumDto model) {
		ResponseDto res = new ResponseDto();
		
		Album album = Optional.ofNullable(model).map(albumMapper::dtoToEntity).orElse(null);
		if(album == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.ALBUM_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				album.setAvatar(optParam.get().getParamValue());
			} else {
				res.setMessage("Không tìm thấy ảnh đại diện mặc định");
				res.setStatus(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.ALBUM_IMAGE.name, name);
				if(imageUrl == null) {
					res.setMessage("Lỗi trong quá trình upload file");
					res.setStatus(false);
					return res;
				}
				album.setAvatar(imageUrl);
			} else {
				res.setMessage("File không hợp lệ");
				res.setStatus(false);
				return res;
			}
		}
		
		Optional<Artist> artist = artistRepo.findById(model.getArtistId());
		if(!artist.isPresent()) return null;
		album.setArtist(artist.get());
		Album savedAlbum = albumRepo.save(album);
		if(savedAlbum != null) {
			res.setStatus(true);
			res.setMessage("Tạo mới thành công");
			return res;
		}
		
		res.setMessage("Không thể tạo mới album");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(AlbumDto model) {
		ResponseDto res = new ResponseDto();
		
		Album album = Optional.ofNullable(model).map(albumMapper::dtoToEntity).orElse(null);
		if(album == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			album.setAvatar(albumRepo.findById(model.getId()).get().getAvatar());
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.ALBUM_IMAGE.name, name);
				if(imageUrl == null) {
					res.setMessage("Lỗi trong quá trình upload file");
					res.setStatus(false);
					return res;
				}
				album.setAvatar(imageUrl);
			} else {
				res.setMessage("File không hợp lệ");
				res.setStatus(false);
				return res;
			}
		}
		
		Optional<Artist> artist = artistRepo.findById(model.getArtistId());
		if(!artist.isPresent()) {
			res.setMessage("Không tìm thấy nghệ sĩ");
			res.setStatus(false);
			return res;
		}
		album.setArtist(artist.get());
		Album savedAlbum = albumRepo.save(album);
		if(savedAlbum != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công");
			return res;
		}
		
		res.setMessage("Không thể cập nhật album");
		res.setStatus(false);
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
			res.setStatus(true);
			res.setMessage("Xóa album thành công");
			return res;
		}
		
		res.setMessage("Không thể tìm thấy album");
		res.setStatus(false);
		return res;
	}

	@Override
	public List<AlbumDto> getTop5ByModifiedDateDesc() {
		return albumRepo.findTop5ByOrderByModifiedDateDesc().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public List<AlbumDto> getTop10ByModifiedDateDesc() {
		return albumRepo.findTop10ByOrderByModifiedDateDesc().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public ResponseDto getAllOrderByName() {
		ResponseDto res = new ResponseDto();
		List<AlbumDto> list = albumRepo.findAll(Sort.by(Sort.Direction.ASC, "name")).stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto finByArtistId(AlbumDto criteria) {
		ResponseDto res = new ResponseDto();
		Page<Album> page = albumRepo.findByArtistId(criteria.getArtistId(), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<AlbumDto> list = page.getContent().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		if(page.getNumber() == 0) {
			pageDto.setFirst(true);
			pageDto.setLast(false);
		} else if(page.getNumber() == page.getTotalPages()) {
			pageDto.setFirst(false);
			pageDto.setLast(true);
		}
		res.setStatus(true);
		res.setContent(pageDto);
		return res;
	}

	@Override
	public Long coutAlbum() {
		return albumRepo.count();
	}

}
