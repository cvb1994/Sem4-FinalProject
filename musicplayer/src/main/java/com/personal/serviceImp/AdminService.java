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
		}
		if(page.getNumber() == page.getTotalPages()-1) {
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
			res.setMessage("T??n t??i kho???n ???? t???n t???i");
			res.setStatus(false);
			return res;
		}
		
		Admin admin = Optional.ofNullable(model).map(adminMapper::dtoToEntity).orElse(null);
		if(admin == null) {
			res.setMessage("D??? li???u kh??ng ????ng");
			res.setStatus(false);
			return res;
		}
		
		admin.setPassword(passEncoder.encode(admin.getPassword()));
		
		Admin savedAdmin = adminRepo.save(admin);
		if(savedAdmin != null) {
			res.setStatus(true);
			res.setMessage("Th??m m???i th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng th??? t???o m???i t??i kho???n admin");
		res.setStatus(false);
		return res;
	}
	
	@Override
	public ResponseDto update(AdminDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<Admin> checkAdmin = adminRepo.findByUsernameAndIdNot(model.getUsername(), model.getId());
		if(checkAdmin != null) {
			res.setMessage("T??n t??i kho???n ???? t???n t???i");
			res.setStatus(false);
			return res;
		}
		
		Optional<Admin> optAdmin = adminRepo.findById(model.getId());
		if(!optAdmin.isPresent()) {
			res.setMessage("T??i kho???n kh??ng t???n t???i");
			res.setStatus(false);
			return res;
		}
		
		Admin admin = optAdmin.get();
		admin.setPassword(passEncoder.encode(model.getPassword()));
		
		Admin savedAdmin = adminRepo.save(admin);
		if(savedAdmin != null) {
			res.setStatus(true);
			res.setMessage("C???p nh???t th??nh c??ng");
			return res;
		}
		
		res.setMessage("C???p nh???t kh??ng th??nh c??ng");
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
			res.setMessage("X??a th??nh c??ng");
			return res;
		}
		
		res.setMessage("Kh??ng t??m th???y t??i kho???n");
		res.setStatus(false);
		return res;
	}

}
