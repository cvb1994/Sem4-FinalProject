package com.personal.common;

public enum FolderTypeEnum {
	GENRE_FOLDER("genreUpload"),
	ARTIST_FOLDER("artistUpload"),
	ALBUM_FOLDER("albumUpload"),
	SONG_FOLDER("songFolder"),
	USER_FOLDER("userUpload"),
	AUDIO_FOLDER("music-upload")
	;
	
	public String name;
	
	private FolderTypeEnum(String name) {
		this.name = name;
	}
}
