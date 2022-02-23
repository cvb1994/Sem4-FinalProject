package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.SystemParamDto;
import com.personal.entity.SystemParam;
import com.personal.mapper.SystemParamMapper;
import com.personal.repository.SystemParamRepository;
import com.personal.service.ISystemParamService;

@Service
public class SystemParamService implements ISystemParamService {
	@Autowired
	private SystemParamRepository systemParamRepo;
	@Autowired
	private SystemParamMapper systemParamMapper;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<SystemParamDto> list = systemParamRepo.findAll().stream().map(systemParamMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto getById(int id) {
		ResponseDto res = new ResponseDto();
		SystemParamDto param = systemParamRepo.findById(id).map(systemParamMapper::entityToDto).orElse(null);
		res.setStatus(true);
		res.setContent(param);
		return res;
	}

	@Override
	public ResponseDto create(SystemParamDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<SystemParam> checkParam = systemParamRepo.findByParamName(model.getParamName());
		if(checkParam.isPresent()) {
			res.setStatus(false);
			res.setMessage("Tên đã tồn tại");
			return res;
		}
		
		SystemParam param = Optional.ofNullable(model).map(systemParamMapper::dtoToEntity).orElse(null);
		if(param == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		SystemParam savedParam = systemParamRepo.save(param);
		if(savedParam != null) {
			res.setStatus(true);
			res.setMessage("Tạo thành công tham số hệ thống");
			return res;
		}
		
		res.setMessage("Không thể tạo tham số hệ thống mới");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(SystemParamDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<SystemParam> checkParam = systemParamRepo.findByParamNameAndIdNot(model.getParamName(), model.getId());
		if(checkParam.isPresent()) {
			res.setStatus(false);
			res.setMessage("Tên đã tồn tại");
			return res;
		}
		
		SystemParam param = Optional.ofNullable(model).map(systemParamMapper::dtoToEntity).orElse(null);
		if(param == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		SystemParam savedParam = systemParamRepo.save(param);
		if(savedParam != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công tham số hệ thống");
			return res;
		}
		
		res.setMessage("Không thể cập nhật tham số hệ thống mới");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto delete(int id) {
		ResponseDto res = new ResponseDto();
		Optional<SystemParam> optParam = systemParamRepo.findById(id);
		if(optParam.isPresent()) {
			SystemParam param = optParam.get();
			systemParamRepo.delete(param);
			res.setStatus(true);
			res.setMessage("Xóa thành công");
			return res;
		}
		
		res.setMessage("Không tìm thấy tham số hệ thống");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto gets(SystemParamDto criteria) {
		ResponseDto res = new ResponseDto();
		
		Page<SystemParam> page = systemParamRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<SystemParamDto> list = page.getContent().stream().map(systemParamMapper::entityToDto).collect(Collectors.toList());
		
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		
		res.setStatus(true);
		res.setContent(pageDto);
		return res;
	}

}
