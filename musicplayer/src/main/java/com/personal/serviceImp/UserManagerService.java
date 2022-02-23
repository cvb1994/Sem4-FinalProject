package com.personal.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.personal.common.UserTypeEnum;
import com.personal.entity.Admin;
import com.personal.entity.User;
import com.personal.entity.UserPrincipal;
import com.personal.repository.AdminRepository;
import com.personal.repository.UserRepository;

@Service
public class UserManagerService implements UserDetailsService{
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AdminRepository	adminRepo;

	@Override
	public UserDetails loadUserByUsername(String usernamewithPrefix) throws UsernameNotFoundException {
		int checkUserName = usernamewithPrefix.indexOf(":");
		if(checkUserName < 0) return null;
		String prefix = usernamewithPrefix.substring(checkUserName+1);
		String actualUserName = usernamewithPrefix.substring(0, checkUserName);
		
		if(prefix.equals(UserTypeEnum.ADMIN.name)) {	
			Optional<Admin> optAdmin = adminRepo.findByUsername(actualUserName);
			if(!optAdmin.isPresent()) throw new UsernameNotFoundException(actualUserName);
			
			Admin admin = optAdmin.get();
			return new UserPrincipal(admin.getId(), admin.getUsername(), admin.getPassword(), UserTypeEnum.ADMIN.name);
		} else {
			Optional<User> optUser = userRepo.findByUsername(actualUserName);
			if(!optUser.isPresent()) throw new UsernameNotFoundException(actualUserName);
			
			User user = optUser.get();
			return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), UserTypeEnum.USER.name);
		}
	}

}
