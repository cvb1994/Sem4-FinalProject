package com.personal.serviceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.common.FolderTypeEnum;
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
import com.personal.utils.CloudStorageUtils;
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
	private CloudStorageUtils uploadCloudStorage;

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
		AlbumDto album = albumRepo.findByIdAndDeletedFalse(albumId).map(albumMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(album);
		return res;
	}

	@Override
	public ResponseDto getByName(String name) {
		ResponseDto res = new ResponseDto();
		AlbumDto album = albumRepo.findByNameAndDeletedFalse(name).map(albumMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(album);
		return res;
	}

	@Override
	public ResponseDto create(AlbumDto model) {
		ResponseDto res = new ResponseDto();
		
		Album album = Optional.ofNullable(model).map(albumMapper::dtoToEntity).orElse(null);
		if(album == null) {
			res.setMessage("D??? li???u kh??ng ????ng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.ALBUM_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				album.setAvatar(optParam.get().getParamValue());
			} else {
				res.setMessage("Kh??ng t??m th???y ???nh ?????i di???n m???c ?????nh");
				res.setStatus(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
//				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.ALBUM_IMAGE.name, name);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.ALBUM_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("L???i trong qu?? tr??nh upload file");
					res.setStatus(false);
					return res;
				}
				album.setAvatar(imageUrl);
			} else {
				res.setMessage("File kh??ng h???p l???");
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
			res.setMessage("T???o m???i th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng th??? t???o m???i album");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(AlbumDto model) {
		ResponseDto res = new ResponseDto();
		
		Album album = Optional.ofNullable(model).map(albumMapper::dtoToEntity).orElse(null);
		if(album == null) {
			res.setMessage("D??? li???u kh??ng ????ng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			album.setAvatar(albumRepo.findById(model.getId()).get().getAvatar());
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.ALBUM_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("L???i trong qu?? tr??nh upload file");
					res.setStatus(false);
					return res;
				}
				album.setAvatar(imageUrl);
			} else {
				res.setMessage("File kh??ng h???p l???");
				res.setStatus(false);
				return res;
			}
		}
		
		Optional<Artist> artist = artistRepo.findById(model.getArtistId());
		if(!artist.isPresent()) {
			res.setMessage("Kh??ng t??m th???y ngh??? s??");
			res.setStatus(false);
			return res;
		}
		album.setArtist(artist.get());
		Album savedAlbum = albumRepo.save(album);
		if(savedAlbum != null) {
			res.setStatus(true);
			res.setMessage("C???p nh???t th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng th??? c???p nh???t album");
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
			res.setMessage("X??a album th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng th??? t??m th???y album");
		res.setStatus(false);
		return res;
	}

	@Override
	public List<AlbumDto> getTop5ByModifiedDateDesc() {
		AlbumDto criteria = new AlbumDto();
		PageRequest limit = PageRequest.of(0, 5, Sort.by("modifiedDate").descending());
		return albumRepo.findAll(albumSpec.filter(criteria),limit).stream().map(albumMapper::entityToDto).collect(Collectors.toList());
//		return albumRepo.findTop5ByOrderByModifiedDateDesc().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public List<AlbumDto> getTop10ByModifiedDateDesc() {
		AlbumDto criteria = new AlbumDto();
		PageRequest limit = PageRequest.of(0, 10, Sort.by("modifiedDate").descending());
		return albumRepo.findAll(albumSpec.filter(criteria),limit).stream().map(albumMapper::entityToDto).collect(Collectors.toList());
//		return albumRepo.findTop10ByOrderByModifiedDateDesc().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public ResponseDto getAllOrderByName() {
		AlbumDto criteria = new AlbumDto();
		ResponseDto res = new ResponseDto();
		List<AlbumDto> list = albumRepo.findAll(albumSpec.filter(criteria), Sort.by(Sort.Direction.ASC, "name")).stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto finByArtistId(AlbumDto criteria) {
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

	@Override
	public int countAlbumNewInMonth() {
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime start = current.withDayOfMonth(1);
		LocalDateTime end = current.withDayOfMonth(current.getDayOfMonth());
		return albumRepo.countByCreatedDateBetween(start, end);
	}

	@Override
	public AlbumDto top1Album() {
		AlbumDto criteria = new AlbumDto();
		PageRequest limit = PageRequest.of(0, 1, Sort.by("totalListen").descending());
		List<AlbumDto> list = albumRepo.findAll(albumSpec.filter(criteria),limit).stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		AlbumDto top1 = list.get(0);
		return top1;
		
//		AlbumDto album = albumRepo.findTop1ByOrderByTotalListenDesc().map(albumMapper::entityToDto).orElse(null);
//		return album;
	}

	@Override
	public List<AlbumDto> searchAlbum(String name) {
		AlbumDto criteria = new AlbumDto();
		criteria.setName(name);
		List<AlbumDto> list = albumRepo.findAll(albumSpec.filter(criteria)).stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		return list;
	}

}
