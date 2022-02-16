package com.personal.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.personal.common.UserTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String username;
	String password;
	String roleType;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal() {
		super();
	}
	
	public UserPrincipal(int id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		if(role.equals(UserTypeEnum.ADMIN.name)) {
			this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			this.authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
