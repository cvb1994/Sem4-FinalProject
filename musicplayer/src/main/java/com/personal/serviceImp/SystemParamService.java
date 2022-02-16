package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<SystemParamDto> getAll() {
		return systemParamRepo.findAll().stream().map(systemParamMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public SystemParamDto getById(int id) {
		return systemParamRepo.findById(id).map(systemParamMapper::entityToDto).orElse(null);
	}

	@Override
	public ResponseDto save(SystemParamDto model) {
		ResponseDto res = new ResponseDto();
		
		if(model.getId() == 0) {
			Optional<SystemParam> checkParam = systemParamRepo.findByParamName(model.getParamName());
			if(checkParam.isPresent()) {
				res.setIsSuccess(false);
				res.setError("Tên đã tồn tại");
				return res;
			}
		}
		
		SystemParam param = Optional.ofNullable(model).map(systemParamMapper::dtoToEntity).orElse(null);
		if(param == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		SystemParam savedParam = systemParamRepo.save(param);
		if(savedParam != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo tham số hệ thống mới");
		res.setIsSuccess(false);
		return res;
	}

	@Override
	public ResponseDto delete(int id) {
		ResponseDto res = new ResponseDto();
		Optional<SystemParam> optParam = systemParamRepo.findById(id);
		if(optParam.isPresent()) {
			SystemParam param = optParam.get();
			systemParamRepo.delete(param);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không tìm thấy tham số hệ thống");
		res.setIsSuccess(false);
		return res;
	}

}
