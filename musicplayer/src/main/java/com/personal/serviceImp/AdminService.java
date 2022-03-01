package com.personal.serviceImp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	@Autowired
	PasswordEncoder passEncoder;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<AdminDto> list = adminRepo.findAll().stream().map(adminMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto gets(AdminDto criteria) {
		ResponseDto res = new ResponseDto();
		
		Page<Admin> page = adminRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<AdminDto> list = page.getContent().stream().map(adminMapper::entityToDto).collect(Collectors.toList());
		
		PageDto pageDto = new PageDto();
		pageDto.setContent(list);
		pageDto.setNumber(page.getNumber());
		pageDto.setNumberOfElements(page.getNumberOfElements());
		pageDto.setPage(page.getNumber());
		pageDto.setSize(page.getSize());
		pageDto.setTotalPages(page.getTotalPages());
		if(page.getNumber() == 0) {
			pageDto.setFirst(true);
			pageDto.setLast(false);
		} else if(page.getNumber() == page.getTotalPages()-1) {
			pageDto.setFirst(false);
			pageDto.setLast(true);
		}
		res.setStatus(true);
		res.setContent(pageDto);
		return res;
	}

	@Override
	public ResponseDto getById(int adminId) {
		ResponseDto res = new ResponseDto();
		
		AdminDto dto = adminRepo.findById(adminId).map(adminMapper::entityToDto).orElse(null);
		
		res.setStatus(true);
		res.setContent(dto);
		return res;
	}

	@Override
	public ResponseDto create(AdminDto model) {
		ResponseDto res = new ResponseDto();

		Optional<Admin> checkAdmin = adminRepo.findByUsername(model.getUsername());
		if(checkAdmin.isPresent()) {
			res.setMessage("Tên tài khoản đã tồn tại");
			res.setStatus(false);
			return res;
		}
		
		Admin admin = Optional.ofNullable(model).map(adminMapper::dtoToEntity).orElse(null);
		if(admin == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		admin.setPassword(passEncoder.encode(admin.getPassword()));
		
		Admin savedAdmin = adminRepo.save(admin);
		if(savedAdmin != null) {
			res.setStatus(true);
			res.setMessage("Thêm mới thành công");
			return res;
		}
		
		res.setMessage("Không thể tạo mới tài khoản admin");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(AdminDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<Admin> checkAdmin = adminRepo.findByUsernameAndIdNot(model.getUsername(), model.getId());
		if(checkAdmin != null) {
			res.setMessage("Tên tài khoản đã tồn tại");
			res.setStatus(false);
			return res;
		}
		
		Optional<Admin> optAdmin = adminRepo.findById(model.getId());
		if(!optAdmin.isPresent()) {
			res.setMessage("Tài khoản không tồn tại");
			res.setStatus(false);
			return res;
		}
		
		Admin admin = optAdmin.get();
		admin.setPassword(passEncoder.encode(model.getPassword()));
		
		Admin savedAdmin = adminRepo.save(admin);
		if(savedAdmin != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công");
			return res;
		}
		
		res.setMessage("Cập nhật không thành công");
		res.setStatus(false);
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
			res.setStatus(true);
			res.setMessage("Xóa thành công");
			return res;
		}
		
		res.setMessage("Không tìm thấy tài khoản");
		res.setStatus(false);
		return res;
	}

}
