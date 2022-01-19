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
import com.personal.dto.GenreDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Genre;
import com.personal.entity.SystemParam;
import com.personal.mapper.GenreMapper;
import com.personal.repository.GenreRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.service.IGenreService;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class GenreService implements IGenreService{
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private GenreMapper genreMapper;
	@Autowired
	Utilities util;
	@Autowired
	private UploadToDrive uploadDrive;

	@Override
	public List<GenreDto> getAll() {
		List<GenreDto> list = genreRepo.findAll().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		
		return list;
	}
	
	@Override
	public PageDto gets(GenreDto criteria) {
		Page<Genre> page = genreRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(),Sort.by("id").descending()));
		List<GenreDto> list = page.getContent().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		
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
	public GenreDto getById(int genreId) {
		GenreDto genre = genreRepo.findById(genreId).map(genreMapper::entityToDto).orElse(null);
		
		return genre;
	}

	@Override
	public GenreDto getByName(String name) {
		GenreDto genre = genreRepo.findByName(name).map(genreMapper::entityToDto).orElse(null);
		
		return genre;
	}

	@Override
	public ResponseDto save(GenreDto model) {
		ResponseDto res = new ResponseDto();
		
		if(model.getId() == 0) {
			Optional<Genre> checkGenre = genreRepo.findByNameIgnoreCase(model.getName());
			if(checkGenre.isPresent()) {
				res.setIsSuccess(false);
				res.setError("Tên đã tồn tại");
				return res;
			}
		}
		Genre genre = Optional.ofNullable(model).map(genreMapper::dtoToEntity).orElse(null);
		if(genre == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		if(model.getFile().isEmpty()) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.GENRE_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				genre.setAvatar(optParam.get().getParamValue());
			} else {
				res.setError("Không tìm thấy ảnh đại diện mặc định");
				res.setIsSuccess(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getName(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.GENRE_IMAGE.name, name);
				if(imageUrl == null) {
					res.setError("Lỗi trong quá trình upload file");
					res.setIsSuccess(false);
					return res;
				}
				genre.setAvatar(imageUrl);
			} else {
				res.setError("File không hợp lệ");
				res.setIsSuccess(false);
				return res;
			}
		}
		
		
		Genre savedGenre = genreRepo.save(genre);
		if(savedGenre != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo thể loại mới");
		res.setIsSuccess(false);
		return res;
	}

	@Override
	public ResponseDto delete(int genreId) {
		ResponseDto res = new ResponseDto();
		Optional<Genre> optGenre = genreRepo.findById(genreId);
		if(optGenre.isPresent()) {
			Genre genre = optGenre.get();
			genre.setDeleted(true);
			genreRepo.save(genre);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không tìm thấy thể loại");
		res.setIsSuccess(false);
		return res;
	}
}
