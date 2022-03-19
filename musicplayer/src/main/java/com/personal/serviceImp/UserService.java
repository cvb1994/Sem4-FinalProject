package com.personal.serviceImp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personal.common.EmailTypeEnum;
import com.personal.common.FolderTypeEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.PageDto;
import com.personal.dto.ResetPasswordDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.UserDto;
import com.personal.entity.SystemParam;
import com.personal.entity.User;
import com.personal.event.SendMailPublisher;
import com.personal.mapper.UserMapper;
import com.personal.repository.PaymentRepository;
import com.personal.repository.SystemParamRepository;
import com.personal.repository.UserRepository;
import com.personal.service.IUserService;
import com.personal.utils.CloudStorageUtils;
import com.personal.utils.UploadToDrive;
import com.personal.utils.Utilities;

@Service
public class UserService implements IUserService{
	@Autowired	
	private UserRepository userRepo;
	@Autowired
	private SystemParamRepository systemRepo;
	@Autowired
	private PaymentRepository paymentRepo;
	@Autowired
	private UploadToDrive uploadDrive;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private Utilities util;
	@Autowired
	PasswordEncoder passEncoder;
	@Autowired
	SendMailPublisher eventPublisher;
	@Autowired
	private CloudStorageUtils uploadCloudStorage;

	@Override
	public ResponseDto getAll() {
		ResponseDto res = new ResponseDto();
		List<UserDto> list =  userRepo.findAll().stream().map(userMapper::entityToDto).collect(Collectors.toList());
		res.setStatus(true);
		res.setContent(list);
		return res;
	}

	@Override
	public ResponseDto gets(UserDto criteria) {
		ResponseDto res = new ResponseDto();
		Page<User> page = userRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<UserDto> list = page.getContent().stream().map(userMapper::entityToDto).collect(Collectors.toList());
		LocalDate current = LocalDate.now();
		list.stream().forEach(u -> {
			if(u.getVipExpireDate() != null) {
				if(current.isBefore(u.getVipExpireDate())) {
					u.setIsVip(true);
				} else {
					u.setIsVip(false);
				}
			} else {
				u.setIsVip(false);
			}
		});
		
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
	public ResponseDto getById(int userId) {
		ResponseDto res = new ResponseDto();
		UserDto userDto =  userRepo.findById(userId).map(userMapper::entityToDto).orElse(null);
		if(userDto != null) {
			if(userDto.getVipExpireDate() != null) {
				LocalDate current = LocalDate.now();
				if(current.isBefore(userDto.getVipExpireDate())) {
					userDto.setIsVip(true);
				} else {
					userDto.setIsVip(false);
				}
			} else {
				userDto.setIsVip(false);
			}
			userDto.setPassword(null);
			res.setStatus(true);
			res.setContent(userDto);
		} else {
			res.setStatus(false);
			res.setMessage("Không tìm thấy tài khoản");
		}
		
		return res;
	}

	@Override
	public ResponseDto getByName(String name) {
		ResponseDto res = new ResponseDto();
		UserDto userDto =  userRepo.findByUsername(name).map(userMapper::entityToDto).orElse(null);
		if(userDto != null) {
			if(userDto.getVipExpireDate() != null) {
				LocalDate current = LocalDate.now();
				if(current.isBefore(userDto.getVipExpireDate())) {
					userDto.setIsVip(true);
				} else {
					userDto.setIsVip(false);
				}
			} else {
				userDto.setIsVip(false);
			}
			userDto.setPassword(null);
			res.setStatus(true);
			res.setContent(userDto);
		} else {
			res.setStatus(false);
			res.setMessage("Không tìm thấy tài khoản");
		}
		
		return res;
	}

	@Override
	public ResponseDto create(UserDto model) {
		ResponseDto res = new ResponseDto();
		
		Optional<User> optUser = userRepo.findByUsername(model.getUsername());
		if(optUser.isPresent()) {
			res.setMessage("Username đã tồn tại");
			res.setStatus(false);
			return res;
		}
		
		Optional<User> listCheckUser = userRepo.findByEmail(model.getEmail());
		if(listCheckUser.isPresent()) {
			res.setMessage("Email đã đăng ký tài khoản");
			res.setStatus(false);
			return res;
		}
		
		User user = Optional.ofNullable(model).map(userMapper::dtoToEntity).orElse(null);
		if(user == null) {
			res.setMessage("Dữ liệu không đúng");
			res.setStatus(false);
			return res;
		}
		
		if(model.getFile() == null) {
			Optional<SystemParam> optParam = systemRepo.findByParamName(SystemParamEnum.USER_IMAGE_DEFAULT.name);
			if(optParam.isPresent()) {
				user.setAvatar(optParam.get().getParamValue());
			} else {
				res.setMessage("Không tìm thấy ảnh đại diện mặc định");
				res.setStatus(false);
				return res;
			}
		} else {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getUsername(), extension);
//				String imageUrl = uploadDrive.uploadImageFile(model.getFile(),FileTypeEnum.USER_IMAGE.name, name);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.USER_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("Lỗi trong quá trình upload file");
					res.setStatus(false);
					return res;
				}
				user.setAvatar(imageUrl);
			} else {
				res.setMessage("File không hợp lệ");
				res.setStatus(false);
				return res;
			}
		}
		user.setPassword(passEncoder.encode(user.getPassword()));
		
		User savedUser = userRepo.save(user);
		if(savedUser != null) {
			res.setStatus(true);
			res.setMessage("Tạo tài khoản thành công");
			eventPublisher.sendMail(savedUser.getFirstName(), savedUser.getEmail(), EmailTypeEnum.WELCOME.name);
			return res;
		}
		
		res.setMessage("Không thể tạo mới user");
		res.setStatus(false);
		return res;
		
	}
	
	@Override
	public ResponseDto update(UserDto model) {
		ResponseDto res = new ResponseDto();
		User user = userRepo.getById(model.getId());
		
		if(model.getUsername() != null) {
			Optional<User> optUser = userRepo.findByUsernameAndIdNot(model.getUsername(), model.getId());
			if(optUser.isPresent()) {
				res.setMessage("Username đã tồn tại");
				res.setStatus(false);
				return res;
			}
		}
		
		if(model.getEmail() != null) {
			Optional<User> listCheckUser = userRepo.findByEmailAndIdNot(model.getEmail(), model.getId());
			if(listCheckUser.isPresent()) {
				res.setMessage("Email đã đăng ký tài khoản");
				res.setStatus(false);
				return res;
			}
			user.setEmail(model.getEmail());
		}
		
		if(model.getFile() != null) {
			String extension = util.getFileExtension(model.getFile());
			if(extension != null) {
				String name = util.nameIdentifier(model.getUsername(), extension);
				String imageUrl = uploadCloudStorage.uploadObject(model.getFile(), name, FolderTypeEnum.USER_IMAGE_FOLDER.name);
				if(imageUrl == null) {
					res.setMessage("Lỗi trong quá trình upload file");
					res.setStatus(false);
					return res;
				}
				user.setAvatar(imageUrl);
			} else {
				res.setMessage("File không hợp lệ");
				res.setStatus(false);
				return res;
			}
		}
		
		user.setPhone(model.getPhone());
		user.setBirthday(model.getBirthday());
		user.setFirstName(model.getFirstName());
		user.setLastName(model.getLastName());
		user.setGender(model.getGender());
		
		User savedUser = userRepo.save(user);
		if(savedUser != null) {
			res.setStatus(true);
			res.setMessage("Cập nhật thành công tài khoản");
			return res;
		}
		
		res.setMessage("Không thể cập nhật tài khoản");
		res.setStatus(false);
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
			res.setMessage("Xóa thành công");
			res.setStatus(true);
			return res;
		}
		res.setMessage("Không thể tìm thấy user");
		res.setStatus(false);
		return res;
	}

	@Override
	public ResponseDto getLinkResetPassword(String email) {
		String baseUrl = "abc";
		ResponseDto res = new ResponseDto();
		Optional<User> optUser = userRepo.findByEmail(email);
		if(!optUser.isPresent()) {
			res.setStatus(false);
			res.setMessage("Email không tồn tại");
			return res;
		}
		
		User user = optUser.get();
		String token = RandomStringUtils.randomAlphabetic(15);
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime expireTime = currentTime.plusDays(1);
		user.setUserToken(token);
		user.setTokenExpire(expireTime);
		userRepo.save(user);
		
		Base64 base64 = new Base64();
		String usernameEncode = new String(base64.encode(user.getUsername().getBytes()));
		String linkReset = baseUrl + "?user="+usernameEncode+"&token="+token;
		
		eventPublisher.sendMail(linkReset, user.getEmail(), EmailTypeEnum.RESET.name);
		res.setStatus(true);
		res.setMessage("Email đặt lại mật khẩu đã được gửi. Vui lòng kiểm tra email của bạn");
		return res;
	}

	@Override
	public ResponseDto resetPassword(ResetPasswordDto model) {
		ResponseDto res = new ResponseDto();
		String userEncode = model.getUser();
		Base64 base64 = new Base64();
		String userDecode = new String(base64.decode(userEncode.getBytes()));
		
		Optional<User> optUser = userRepo.findByUsername(userDecode);
		if(!optUser.isPresent()) {
			res.setStatus(false);
			res.setMessage("User không tồn tại");
			return res;
		}
		User user = optUser.get();
		if(!user.getUserToken().equals(model.getToken())) {
			res.setStatus(false);
			res.setMessage("Token không hợp lệ");
			return res;
		}
		LocalDateTime current = LocalDateTime.now();
		if(current.isAfter(user.getTokenExpire())) {
			res.setStatus(false);
			res.setMessage("Token đã hết hạn");
			return res;
		}
		
		user.setPassword(passEncoder.encode(model.getNewPass()));
		userRepo.save(user);
		res.setStatus(true);
		res.setMessage("Mật khẩu đã được cập nhật");
		return res;
	}

	@Override
	public int countUserNewInMonth() {
		LocalDateTime current = LocalDateTime.now();
		LocalDateTime start = current.withDayOfMonth(1);
		LocalDateTime end = current.withDayOfMonth(current.getDayOfMonth());
		return userRepo.countByCreatedDateBetween(start, end);
	}

	@Override
	public Long countUser() {
		return userRepo.count();
	}

}
