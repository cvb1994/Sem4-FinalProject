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
import com.personal.common.FileTypeEnum;
import com.personal.common.SystemParamEnum;
import com.personal.dto.PageDto;
import com.personal.dto.ResetPasswordDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.UserDto;
import com.personal.entity.Payment;
import com.personal.entity.SystemParam;
import com.personal.entity.User;
import com.personal.event.SendMailPublisher;
import com.personal.mapper.UserMapper;
import com.personal.repository.PaymentRepository;
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
	

	@Override
	public List<UserDto> getAll() {
		return userRepo.findAll().stream().map(userMapper::entityToDto).collect(Collectors.toList());
	}

	@Override
	public PageDto gets(UserDto criteria) {
		Page<User> page = userRepo.findAll(PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by("id").descending()));
		List<UserDto> list = page.getContent().stream().map(userMapper::entityToDto).collect(Collectors.toList());
		LocalDate current = LocalDate.now();
		list.stream().forEach(u -> {
			Optional<Payment> optPayment = paymentRepo.findByUserIdAndStatusActiveTrue(u.getId());
			if(optPayment.isPresent()) {
				if(current.isBefore(optPayment.get().getExpireDate())) {
					u.setIsVip(true);
				} else {
					u.setIsVip(false);
				}
				u.setExpireDate(optPayment.get().getExpireDate());
			}
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
	public UserDto getById(int userId) {
		UserDto userDto =  userRepo.findById(userId).map(userMapper::entityToDto).orElse(null);
		if(userDto != null) {
			Optional<Payment> optPayment = paymentRepo.findByUserIdAndStatusActiveTrue(userDto.getId());
			if(optPayment.isPresent()) {
				LocalDate current = LocalDate.now();
				if(current.isBefore(optPayment.get().getExpireDate())) {
					userDto.setIsVip(true);
				} else {
					userDto.setIsVip(false);
				}
				userDto.setExpireDate(optPayment.get().getExpireDate());
			}
		}
		return userDto;
	}

	@Override
	public UserDto getByName(String name) {
		UserDto userDto =  userRepo.findByUsername(name).map(userMapper::entityToDto).orElse(null);
		if(userDto != null) {
			Optional<Payment> optPayment = paymentRepo.findByUserIdAndStatusActiveTrue(userDto.getId());
			if(optPayment.isPresent()) {
				LocalDate current = LocalDate.now();
				if(current.isBefore(optPayment.get().getExpireDate())) {
					userDto.setIsVip(true);
				} else {
					userDto.setIsVip(false);
				}
				userDto.setExpireDate(optPayment.get().getExpireDate());
			}
		}
		return userDto;
	}

	@Override
	public ResponseDto save(UserDto model) {
		ResponseDto res = new ResponseDto();
		
		if(model.getId() != 0) {
			Optional<User> optUser = userRepo.findByUsernameAndIdNot(model.getUsername(), model.getId());
			if(optUser.isPresent()) {
				res.setError("Username đã tồn tại");
				res.setIsSuccess(false);
				return res;
			}
		}
		
		List<User> listCheckUser = userRepo.findByEmailOrPhone(model.getEmail(), model.getPhone());
		if(listCheckUser.size() > 0) {
			res.setError("Email hoặc số điện thoại đã có");
			res.setIsSuccess(false);
			return res;
		}
		
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
		
		if(model.getFile() == null) {
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
			eventPublisher.sendMail(savedUser.getFirstName(), savedUser.getEmail(), EmailTypeEnum.WELCOME.name);
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

	@Override
	public ResponseDto getLinkResetPassword(String email) {
		String baseUrl = "abc";
		ResponseDto res = new ResponseDto();
		Optional<User> optUser = userRepo.findByEmail(email);
		if(!optUser.isPresent()) {
			res.setIsSuccess(false);
			res.setError("Email không tồn tại");
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
		res.setIsSuccess(true);
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
			res.setIsSuccess(false);
			res.setError("User không tồn tại");
			return res;
		}
		User user = optUser.get();
		if(!user.getUserToken().equals(model.getToken())) {
			res.setIsSuccess(false);
			res.setError("Token không hợp lệ");
			return res;
		}
		LocalDateTime current = LocalDateTime.now();
		if(current.isAfter(user.getTokenExpire())) {
			res.setIsSuccess(false);
			res.setError("Token hết hạn");
			return res;
		}
		
		user.setPassword(passEncoder.encode(model.getNewPass()));
		userRepo.save(user);
		res.setIsSuccess(true);
		return res;
	}

}
