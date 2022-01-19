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
import com.personal.dto.ArtistDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Artist;
import com.personal.entity.SystemParam;
import com.personal.mapper.ArtistMapper;
import com.personal.repository.ArtistRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.service.IArtistService;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class ArtistService implements IArtistService{
	@Autowired
	private ArtistRepository artistRepo;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private ArtistMapper artistMapper;
	@Autowired
	private Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;

	@Override
	public List<ArtistDto> getAll() {
		List<ArtistDto> list =  artistRepo.findAll().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		
		return list;
	}

	@Override
	public PageDto gets(ArtistDto criteria) {
		Page<Artist> page = artistRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(),Sort.by("id").descending()));
		List<ArtistDto> list = page.getContent().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		
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
	public ArtistDto getById(int artistId) {
		ArtistDto artist = artistRepo.findById(artistId).map(artistMapper::entityToDto).orElse(null);
		
		return artist;
	}

	@Override
	public ArtistDto getByName(String name) {
		ArtistDto artist =  artistRepo.findByName(name).map(artistMapper::entityToDto).orElse(null);
		
		return artist;
	}

	@Override
	public ResponseDto save(ArtistDto model) {
		ResponseDto res = new ResponseDto();
		Artist artist = Optional.ofNullable(model).map(artistMapper::dtoToEntity).orElse(null);
		if(artist == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		if(model.getFile().isEmpty()) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.ARTIST_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				artist.setAvatar(optParam.get().getParamValue());
			} else {
				res.setError("Không tìm thấy ảnh đại diện mặc định");
				res.setIsSuccess(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.ARTIST_IMAGE.name, name);
				if(imageUrl == null) {
					res.setError("Lỗi trong quá trình upload file");
					res.setIsSuccess(false);
					return res;
				}
				artist.setAvatar(imageUrl);
			} else {
				res.setError("File không hợp lệ");
				res.setIsSuccess(false);
				return res;
			}
		}
		
		Artist savedArtist =  artistRepo.save(artist);
		if(savedArtist != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo nghệ sĩ mới");
		res.setIsSuccess(false);
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
			res.setIsSuccess(true);
			return res;
		}
		res.setError("Không thể tìm thấy nghệ sĩ");
		res.setIsSuccess(false);
		return res;
	}

}
