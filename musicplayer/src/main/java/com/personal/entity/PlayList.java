package com.personal.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "playlist")
@Getter
@Setter
public class PlayList extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "playlist_song",
		joinColumns = @JoinColumn(name="playlist_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name="song_id", referencedColumnName = "id"))
	private List<Song> songs;
}
