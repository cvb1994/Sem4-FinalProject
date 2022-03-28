package com.personal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.common.UserTypeEnum;
import com.personal.dto.AdminDto;
import com.personal.dto.LoginDto;
import com.personal.dto.ResponseDto;
import com.personal.dto.UserDto;
import com.personal.entity.UserPrincipal;
import com.personal.serviceImp.AdminService;
import com.personal.serviceImp.UserService;
import com.personal.utils.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
	@Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userSer;
    @Autowired
    private AdminService adminSer;
    
    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@ModelAttribute LoginDto model) {
    	String loginUserName = model.getUsername().concat(":").concat(UserTypeEnum.ADMIN.name);
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginUserName, model.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();
            
            String jwt = tokenProvider.generateToken(principal, UserTypeEnum.ADMIN.name);
            ResponseDto res = adminSer.getById(principal.getId());
            AdminDto adminDto = (AdminDto) res.getContent();
            adminDto.setJwt(jwt);
            adminDto.setPassword(null);
            res.setContent(adminDto);

            return ResponseEntity.ok(res);
        } catch (BadCredentialsException ex) {
        	ResponseDto res = new ResponseDto();
        	res.setStatus(false);
        	res.setMessage("Username hoặc Password không đúng");
            return ResponseEntity.ok(res);
        }
    }
    
    @PostMapping("/user/login")
    public ResponseEntity<?> userLogin(@ModelAttribute LoginDto model) {
    	String loginUserName = model.getUsername().concat(":").concat(UserTypeEnum.USER.name);
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginUserName, model.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();
            
            String jwt = tokenProvider.generateToken(principal, UserTypeEnum.USER.name);
            ResponseDto res = userSer.getById(principal.getId());
            UserDto userDto = (UserDto) res.getContent();
            userDto.setJwtToken(jwt);
            userDto.setPassword(null);
            res.setContent(userDto);

            return ResponseEntity.ok(res);
        } catch (BadCredentialsException ex) {
        	ResponseDto res = new ResponseDto();
        	res.setStatus(false);
        	res.setMessage("Username hoặc Password không đúng");
            return ResponseEntity.ok(res);
        }
    }
    
    @PostMapping("/logout")
	public ResponseEntity<?> logout() {
    	ResponseDto res = new ResponseDto();
    	res.setStatus(true);
		return ResponseEntity.ok(res);
	}
    
    
}
