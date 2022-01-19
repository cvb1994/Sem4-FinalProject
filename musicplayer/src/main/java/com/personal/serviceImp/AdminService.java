package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.personal.dto.AdminDto;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.entity.Admin;
import com.personal.mapper.AdminMapper;
import com.personal.repository.AdminRepository;
import com.personal.service.IAdminService;

@Service
public class AdminService implements IAdminService{
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public List<AdminDto> getAll() {
		return adminRepo.findAll().stream().map(adminMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public PageDto gets(AdminDto criteria) {
		Page<Admin> page = adminRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<AdminDto> list = page.getContent().stream().map(adminMapper::entityToDto).collect(Collectors.toList());
		
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
	public AdminDto getById(int adminId) {
		return adminRepo.findById(adminId).map(adminMapper::entityToDto).orElse(null);
	}

	@Override
	public AdminDto getByName(String name) {
//		return adminRepo.findByUsername(name).map(adminMapper::entityToDto).orElse(null);
		return null;
	}

	@Override
	public ResponseDto save(AdminDto model) {
		ResponseDto res = new ResponseDto();
		if(model.getId() == 0) {
			Admin admin = adminRepo.findByUsername(model.getUsername());
			if(admin != null) {
				res.setError("Tên tài khoản đã tồn tại");
				res.setIsSuccess(false);
				return res;
			}
		}
		Admin admin = Optional.ofNullable(model).map(adminMapper::dtoToEntity).orElse(null);
		if(admin == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		Admin savedAdmin = adminRepo.save(admin);
		if(savedAdmin != null) {
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không thể tạo mới tài khoản admin");
		res.setIsSuccess(false);
		return res;
	}

	@Override
	public ResponseDto delete(int adminId) {
		ResponseDto res = new ResponseDto();
		Optional<Admin> optAdmin = adminRepo.findById(adminId);
		if(optAdmin.isPresent()) {
			Admin admin = optAdmin.get();
			admin.setDeleted(true);
			adminRepo.save(admin);
			res.setIsSuccess(true);
			return res;
		}
		
		res.setError("Không tìm thấy tài khoản");
		res.setIsSuccess(false);
		return res;
	}

}
