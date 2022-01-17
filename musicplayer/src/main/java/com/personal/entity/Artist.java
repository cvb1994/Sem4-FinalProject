package com.personal.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "artist")
@Getter
@Setter
public class Artist extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "avatar", nullable = false)
	private String avatar;
	
	@Column(name = "gender", nullable = true)
	private String gender;
	
	@Column(name = "birthday", nullable = true)
	private LocalDate birthday;
	
	@Column(name = "description", columnDefinition="TEXT", nullable = true)
	private String description;
	
	@Column(name = "nationality", nullable = true)
	private String nationality;
	
	@ManyToMany(mappedBy = "artists")
	private List<Song> songs;
	
	@OneToMany(mappedBy = "artist")
	private List<Album> albums;
	
}
