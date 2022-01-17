package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.dto.ArtistDto;
import com.personal.dto.PageDto;
import com.personal.entity.Artist;
import com.personal.mapper.ArtistMapper;
import com.personal.repository.ArtistRepository;
import com.personal.service.IArtistService;

@Service
public class ArtistService implements IArtistService{
	@Autowired
	private ArtistRepository artistRepo;
	@Autowired
	private ArtistMapper artistMapper;
	
	private String serviceUrl = "http://localhost:8081/upload/img/";

	@Override
	public List<ArtistDto> getAll() {
		List<ArtistDto> list =  artistRepo.findAll().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(a -> {
			a.setAvatar(serviceUrl.concat(a.getAvatar()));
		});
		return list;
	}

	@Override
	public PageDto gets(ArtistDto criteria) {
		Page<Artist> page = artistRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(),Sort.by("id").descending()));
		List<ArtistDto> list = page.getContent().stream().map(artistMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(a -> {
			a.setAvatar(serviceUrl.concat(a.getAvatar()));
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
	public ArtistDto getById(int artistId) {
		ArtistDto artist = artistRepo.findById(artistId).map(artistMapper::entityToDto).orElse(null);
		if(artist != null) {
			artist.setAvatar(serviceUrl.concat(artist.getAvatar()));
		}
		return artist;
	}

	@Override
	public ArtistDto getByName(String name) {
		ArtistDto artist =  artistRepo.findByName(name).map(artistMapper::entityToDto).orElse(null);
		if(artist != null) {
			artist.setAvatar(serviceUrl.concat(artist.getAvatar()));
		}
		return artist;
	}

	@Override
	public ArtistDto save(ArtistDto model) {
		Artist artist = Optional.ofNullable(model).map(artistMapper::dtoToEntity).orElse(null);
		if (artist != null) {
			return Optional.ofNullable(artistRepo.save(artist)).map(artistMapper::entityToDto).orElse(null);
		} else {
			return null;
		}
	}

	@Override
	public boolean delete(int artistId) {
		Optional<Artist> artist = artistRepo.findById(artistId);
		if(artist.isPresent()) {
			artistRepo.delete(artist.get());
			return true;
		}
		return false;
	}

}
