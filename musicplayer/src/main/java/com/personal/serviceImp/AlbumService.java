package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.dto.AlbumDto;
import com.personal.dto.PageDto;
import com.personal.entity.Album;
import com.personal.entity.Artist;
import com.personal.mapper.AlbumMapper;
import com.personal.mapper.ArtistMapper;
import com.personal.mapper.SongMapper;
import com.personal.repository.AlbumRepository;
import com.personal.repository.ArtistRepository;
import com.personal.repository.SongRepository;
import com.personal.service.IAlbumService;

@Service
public class AlbumService implements IAlbumService {
	@Autowired
	private AlbumRepository albumRepo;
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private ArtistRepository artistRepo;
	@Autowired
	private ArtistMapper artistMapper;
	@Autowired
	private AlbumMapper albumMapper;
	@Autowired
	private SongMapper songMapper;
	
	private String serviceUrl = "http://localhost:8081/upload/img/";

	@Override
	public List<AlbumDto> getAll() {
		List<AlbumDto> list = albumRepo.findAll().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
		list.stream().forEach(a -> {
			a.setAvatar(serviceUrl.concat(a.getAvatar()));
		});
		return list;
	}

	@Override
	public PageDto gets(AlbumDto criteria) {
		Page<Album> page = albumRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<AlbumDto> list = page.getContent().stream().map(albumMapper::entityToDto).collect(Collectors.toList());
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
	public AlbumDto getById(int albumId) {
		AlbumDto album = albumRepo.findById(albumId).map(albumMapper::entityToDto).orElse(null);
		if(album != null) {
			album.setAvatar(serviceUrl.concat(album.getAvatar()));
		}
		return album;
	}

	@Override
	public AlbumDto getByName(String name) {
		AlbumDto album = albumRepo.findByName(name).map(albumMapper::entityToDto).orElse(null);
		if(album != null) {
			album.setAvatar(serviceUrl.concat(album.getAvatar()));
		}
		return album;
	}

	@Override
	public AlbumDto save(AlbumDto model) {
		Album album = Optional.ofNullable(model).map(albumMapper::dtoToEntity).orElse(null);
		if (album != null) {
			Optional<Artist> artist = artistRepo.findById(model.getArtistId());
			if(!artist.isPresent()) return null;
			album.setArtist(artist.get());
			return Optional.ofNullable(albumRepo.save(album)).map(albumMapper::entityToDto).orElse(null);
		} else {
			return null;
		}
	}

	@Override
	public boolean delete(int albumId) {
		Optional<Album> album = albumRepo.findById(albumId);
		if(album.isPresent()) {
			albumRepo.delete(album.get());
			return true;
		}
		return false;
	}

}
