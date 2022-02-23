package com.personal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "phone", nullable = true)
	private String phone;
	
	@Column(name = "birthday", nullable = true)
	private LocalDate birthday;
	
	@Column(name = "first_name", nullable = true)
	private String firstName;
	
	@Column(name = "last_name", nullable = true)
	private String lastName;
	
	@Column(name = "gender", nullable = true)
	private String gender;
	
	@Column(name = "avatar", nullable = false)
	private String avatar;
	
	@Column(name = "vip_expire_date", nullable = false)
	private LocalDate vipExpireDate;
	
	@Column(name = "user_token", nullable = true)
	private String userToken;
	
	@Column(name = "token_expire", nullable = true)
	private LocalDateTime tokenExpire;
	
	@OneToMany(mappedBy = "user")
	private List<PlayList> playlists;
	
	@OneToMany(mappedBy = "user")
	private List<Payment> payments;
}
