package com.personal.entity;

import java.util.List;

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
@Table(name = "song")
@Getter
@Setter
public class Song extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "image", nullable = false)
	private String image;
	
	@Column(name = "media_Url", nullable = false)
	private String mediaUrl;
	
	@Column(name = "time_play", nullable = true)
	private String timePlay;
	
	@Column(name = "composer", nullable = true)
	private String composer;
	
	@Column(name = "listen_count", nullable = true)
	private int listenCount;
	
	@Column(name = "listen_count_reset", nullable = true)
	private int listenCountReset;
	
	@Column(name = "vip_only", nullable = false)
	private Boolean vipOnly;
	
	@ManyToMany
	@JoinTable(name = "song_artist",
				joinColumns = @JoinColumn(name="song_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name="artist_id", referencedColumnName = "id"))
	private List<Artist> artists;
	
	@ManyToOne
	@JoinColumn(name = "albumId")
	private Album album;
	
	@ManyToMany
	@JoinTable(name = "song_genre",
				joinColumns = @JoinColumn(name="song_id", referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name="genre_id", referencedColumnName = "id"))
	private List<Genre> genres;
	
	@ManyToMany(mappedBy = "songs")
	private List<PlayList> playlists;
	
}
