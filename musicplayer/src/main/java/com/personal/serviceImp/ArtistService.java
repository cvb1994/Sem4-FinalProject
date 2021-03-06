package com.personal.serviceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.common.FolderTypeEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.ArtistDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Artist;
import com.personal.entity.SystemParam;
import com.personal.mapper.ArtistMapper;
import com.personal.musicplayer.specification.ArtistSpecification;
import com.personal.repository.ArtistRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.service.IArtistService;
import com.personal.utils.CloudStorageUtils;
import com.personal.utils.Utilities;

@Service
public class ArtistService implements IArtistService{
	@Autowired
	private ArtistRepository artistRepo;
	@Autowired
	private ArtistSpecification artistSpec;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private ArtistMapper artistMapper;
	@Autowired
	private Utilities util;
	@Autowired
	private CloudStorageUtils uploadCloudStorage;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<ArtistDto> list =  artistRepo.findAll().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto gets(ArtistDto criteria) {
		ResponseDto res = new ResponseDto();
		Page<Artist> page = artistRepo.findAll(artistSpec.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(),Sort.by("id").descending()));
		List<ArtistDto> list = page.getContent().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		
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
	public ResponseDto getById(int artistId) {
		ResponseDto res = new ResponseDto();
		ArtistDto artist = artistRepo.findByIdAndDeletedFalse(artistId).map(artistMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(artist);
		return res;
	}

	@Override
	public ResponseDto getByName(String name) {
		ResponseDto res = new ResponseDto();
		ArtistDto artist =  artistRepo.findByNameAndDeletedFalse(name).map(artistMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(artist);
		return res;
	}

	@Override
	public ResponseDto create(ArtistDto model) {
		ResponseDto res = new ResponseDto();
		Artist artist = Optional.ofNullable(model).map(artistMapper::dtoToEntity).orElse(null);
		if(artist == null) {
			res.setMessage("D??? li???u kh??ng ????ng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.ARTIST_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				artist.setAvatar(optParam.get().getParamValue());
			} else {
				res.setMessage("Kh??ng t??m th???y ???nh ?????i di???n m???c ?????nh");
				res.setStatus(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
//				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.ARTIST_IMAGE.name, name);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.ARTIST_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("L???i trong qu?? tr??nh upload file");
					res.setStatus(false);
					return res;
				}
				artist.setAvatar(imageUrl);
			} else {
				res.setMessage("File kh??ng h???p l???");
				res.setStatus(false);
				return res;
			}
		}
		
		Artist savedArtist =  artistRepo.save(artist);
		if(savedArtist != null) {
			res.setStatus(true);
			res.setMessage("T???o m???i th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng th??? t???o ngh??? s?? m???i");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(ArtistDto model) {
		ResponseDto res = new ResponseDto();
		Artist artist = Optional.ofNullable(model).map(artistMapper::dtoToEntity).orElse(null);
		if(artist == null) {
			res.setMessage("D??? li???u kh??ng ????ng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			artist.setAvatar(artistRepo.findById(model.getId()).get().getAvatar());
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.ARTIST_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("L???i trong qu?? tr??nh upload file");
					res.setStatus(false);
					return res;
				}
				artist.setAvatar(imageUrl);
			} else {
				res.setMessage("File kh??ng h???p l???");
				res.setStatus(false);
				return res;
			}
		}
		
		Artist savedArtist =  artistRepo.save(artist);
		if(savedArtist != null) {
			res.setStatus(true);
			res.setMessage("C???p nh???t th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng th??? c???p nh???t ngh??? s??");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto delete(int artistId) {
		ResponseDto res = new ResponseDto();
		Optional<Artist> optArtist = artistRepo.findById(artistId);
		if(optArtist.isPresent()) {
			Artist artist = optArtist.get();
			artist.setDeleted(true);
			artistRepo.save(artist);
			res.setStatus(true);
			res.setMessage("X??a th??nh c??ng");
			return res;
		}
		res.setMessage("Kh??ng th??? t??m th???y ngh??? s??");
		res.setStatus(false);
		return res;
	}

	@Override
	public List<ArtistDto> getTop10ByModifiedDateDesc() {
		ArtistDto criteria = new ArtistDto();
		PageRequest limit = PageRequest.of(0, 10, Sort.by("modifiedDate").descending());
		return artistRepo.findAll(artistSpec.filter(criteria),limit).stream().map(artistMapper::entityToDto).collect(Collectors.toList());
//		return artistRepo.findTop10ByOrderByModifiedDateDesc().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public ResponseDto getListArtistOrderByName() {
		ResponseDto res = new ResponseDto();
		ArtistDto criteria = new ArtistDto();
		List<ArtistDto> list =  artistRepo.findAll(artistSpec.filter(criteria), Sort.by(Sort.Direction.ASC, "name")).stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public Long countArtist() {
		return artistRepo.count();
	}

	@Override
	public int countArtistNewInMonth() {
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime start = current.withDayOfMonth(1);
		LocalDateTime end = current.withDayOfMonth(current.getDayOfMonth());
		return artistRepo.countByCreatedDateBetween(start, end);
	}

	@Override
	public List<ArtistDto> getTopArtist() {
//		return artistRepo.findTopArtist().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
		List<ArtistDto> list = artistRepo.findAll(artistSpec.filterTop(),pageable).stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		return list;
	}

	@Override
	public List<ArtistDto> searchArtist(String name) {
		ArtistDto criteria = new ArtistDto();
		criteria.setName(name);
		List<ArtistDto> list = artistRepo.findAll(artistSpec.filter(criteria)).stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		return list;
	}

}
