package com.personal.serviceImp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.dto.GenreDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Genre;
import com.personal.mapper.GenreMapper;
import com.personal.repository.GenreRepository;
import com.personal.service.IGenreService;
import com.personal.utils.Utilities;

@Service
public class GenreService implements IGenreService{
	@Autowired
	private GenreRepository genreRepo;
	@Autowired
	private GenreMapper genreMapper;
	@Autowired
	Utilities util;
	
	private String serviceUrl = "http://localhost:8081/upload/img/";

	@Override
	public List<GenreDto> getAll() {
		List<GenreDto> list = genreRepo.findAll().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(g -> {
			g.setAvatar(serviceUrl.concat(g.getAvatar()));
		});
		return list;
	}
	
	@Override
	public PageDto gets(GenreDto criteria) {
		Page<Genre> page = genreRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(),Sort.by("id").descending()));
		List<GenreDto> list = page.getContent().stream().map(genreMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(g -> {
			g.setAvatar(serviceUrl.concat(g.getAvatar()));
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
	public GenreDto getById(int genreId) {
		GenreDto genre = genreRepo.findById(genreId).map(genreMapper::entityToDto).orElse(null);
		if(genre != null) {
			genre.setAvatar(serviceUrl.concat(genre.getAvatar()));
		}
		return genre;
	}

	@Override
	public GenreDto getByName(String name) {
		GenreDto genre = genreRepo.findByName(name).map(genreMapper::entityToDto).orElse(null);
		if(genre != null) {
			genre.setAvatar(serviceUrl.concat(genre.getAvatar()));
		}
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
		
		String fileName = null;
		try {
			fileName = util.copyFile(model.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setAvatar(fileName);
		
		Genre genre = Optional.ofNullable(model).map(genreMapper::dtoToEntity).orElse(null);
		if (genre != null) {
			genreRepo.save(genre);
			res.setIsSuccess(true);
			return res;
		} else {
			return null;
		}
	}

	@Override
	public ResponseDto delete(int genreId) {
		ResponseDto res = new ResponseDto();
		Optional<Genre> genre = genreRepo.findById(genreId);
		if(genre.isPresent()) {
			genreRepo.delete(genre.get());
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không tìm thấy thể loại");
		res.setIsSuccess(false);
		return res;
	}
}
