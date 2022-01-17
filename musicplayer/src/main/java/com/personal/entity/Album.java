package com.personal.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "album")
@Setter
@Getter
public class Album extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "avatar", nullable = false)
	private String avatar;
	
	@Column(name = "total_time", nullable = true)
	private String totalTime;
	
	@Column(name = "release_date", nullable = true)
	private LocalDate releaseDate;
	
	@Column(name = "total_listen", columnDefinition="integer default 0")
	private int totalListen;
	
	@ManyToOne
	@JoinColumn(name = "artistId", nullable = false)
	private Artist artist;
	
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	private List<Song> songs;
	
	
}
