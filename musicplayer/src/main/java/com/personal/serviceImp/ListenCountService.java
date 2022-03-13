package com.personal.serviceImp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.personal.dto.ListenCountDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.SongSimplyDto;
import com.personal.entity.ListenCount;
import com.personal.mapper.ListenCountMapper;
import com.personal.mapper.SongMapper;
import com.personal.musicplayer.specification.ListenCountSpecification;
import com.personal.repository.ListenCountRepository;
import com.personal.repository.SongRepository;
import com.personal.service.IListenCountService;

@Service
public class ListenCountService implements IListenCountService {
	@Autowired
	private ListenCountRepository listenCountRepo;
	@Autowired
	private SongRepository songRepo;
	@Autowired
	private SongMapper songMapper;
	@Autowired
	private ListenCountMapper listenCountMapper;
	@Autowired
	private ListenCountSpecification listenCountSpec;
	

	@Override
	public void saveTopTrendingBeforeReset() {
		LocalDateTime current = LocalDateTime.now();
		List<SongSimplyDto> listTopSong = songRepo.findTop10ByOrderByListenCountResetDesc().stream().map(songMapper::entityToSimplyDto).collect(Collectors.toList());
		
		ObjectMapper map = new ObjectMapper();
		map.enable(SerializationFeature.INDENT_OUTPUT);
		String content = null;
		try {
			content = map.writeValueAsString(listTopSong);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListenCount lc = new ListenCount();
		lc.setMonth(current.getMonthValue());
		lc.setYear(current.getYear());
		lc.setContent(content);
		listenCountRepo.save(lc);
	}

	@Override
	public ResponseDto gets(ListenCountDto criteria) {
		ResponseDto res = new ResponseDto();
		
		Page<ListenCount> page = listenCountRepo.findAll(listenCountSpec.filter(criteria), PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<ListenCountDto> list = page.getContent().stream().map(listenCountMapper::entityToDto).collect(Collectors.toList());
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
	public ResponseDto getById(int id) {
		ResponseDto res = new ResponseDto();
		Optional<ListenCount> optListenCount = listenCountRepo.findById(id);
		ObjectMapper mapper = new ObjectMapper();
		
		if(optListenCount.isPresent()) {
			ListenCount lc = optListenCount.get();
			ListenCountDto dto = new ListenCountDto();
			List<SongSimplyDto> listSong = new ArrayList<>();
			dto.setMonth(lc.getMonth());
			dto.setYear(lc.getYear());
			try {
				listSong = mapper.readValue(lc.getContent(), new TypeReference<List<SongSimplyDto>>() {});
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			dto.setListSong(listSong);
			
			res.setStatus(true);
			res.setContent(dto);
			
		}
		return res;
	}

}
