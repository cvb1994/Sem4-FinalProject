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

import com.personal.common.FileTypeEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.PageDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.UserDto;
import com.personal.entity.SystemParam;
import com.personal.entity.User;
import com.personal.mapper.UserMapper;
import com.personal.repository.SystemParamRepository;
import com.personal.repository.UserRepository;
import com.personal.service.IUserService;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class UserService implements IUserService{
	@Autowired	
	private UserRepository userRepo;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private UploadToDrive uploadDrive;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private Utilities util;
	@Autowired
	PasswordEncoder passEncoder;
	

	@Override
	public List<UserDto> getAll() {
		return userRepo.findAll().stream().map(userMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public PageDto gets(UserDto criteria) {
		Page<User> page = userRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<UserDto> list = page.getContent().stream().map(userMapper::entityToDto).collect(Collectors.toList());
		
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
	public UserDto getById(int userId) {
		return userRepo.findById(userId).map(userMapper::entityToDto).orElse(null);
	}

	@Override
	public UserDto getByName(String name) {
		return userRepo.findByUsername(name).map(userMapper::entityToDto).orElse(null);
	}

	@Override
	public ResponseDto save(UserDto model) {
		ResponseDto res = new ResponseDto();
		
//		if(model.getId() != 0) {
//			Optional<User> optUser = userRepo.findById(model.getId());
//			if(optUser.isPresent()) {
//				res.setError("Username đã tồn tại");
//				res.setIsSuccess(false);
//				return res;
//			}
//		}
		
		Optional<User> optUser = userRepo.findByUsername(model.getUsername());
		if(optUser.isPresent()) {
			res.setError("Username đã tồn tại");
			res.setIsSuccess(false);
			return res;
		}
		
		User user = Optional.ofNullable(model).map(userMapper::dtoToEntity).orElse(null);
		if(user == null) {
			res.setError("Dữ liệu không đúng");
			res.setIsSuccess(false);
			return res;
		}
		
		if(model.getFile().isEmpty()) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.USER_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				user.setAvatar(optParam.get().getParamValue());
			} else {
				res.setError("Không tìm thấy ảnh đại diện mặc định");
				res.setIsSuccess(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getUsername(), extension);
				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.USER_IMAGE.name, name);
				if(imageUrl == null) {
					res.setError("Lỗi trong quá trình upload file");
					res.setIsSuccess(false);
					return res;
				}
				user.setAvatar(imageUrl);
			} else {
				res.setError("File không hợp lệ");
				res.setIsSuccess(false);
				return res;
			}
		}
		user.setPassword(passEncoder.encode(user.getPassword()));
		
		User savedUser = userRepo.save(user);
		if(savedUser != null) {
			res.setIsSuccess(true);
			
			return res;
		}
		
		res.setError("Không thể tạo mới user");
		res.setIsSuccess(false);
		return res;
		
	}

	@Override
	public ResponseDto delete(int userId) {
		ResponseDto res = new ResponseDto();
		Optional<User> optUser = userRepo.findById(userId);
		if(optUser.isPresent()) {
			User user = optUser.get();
			user.setDeleted(true);
			userRepo.save(user);
			res.setIsSuccess(true);
			return res;
		}
		res.setError("Không thể tìm thấy user");
		res.setIsSuccess(false);
		return res;
	}

}
