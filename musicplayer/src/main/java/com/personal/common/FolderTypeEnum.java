package com.personal.common;

public enum FolderTypeEnum {
	GENRE_IMAGE_FOLDER("genreUpload"),
	ARTIST_IMAGE_FOLDER("artistUpload"),
	ALBUM_IMAGE_FOLDER("albumUpload"),
	SONG_IMAGE_FOLDER("songFolder"),
	USER_IMAGE_FOLDER("userUpload"),
	AUDIO_FOLDER("music-upload")
	;
	
	public String name;
	
	private FolderTypeEnum(String name) {
		this.name = name;
	}
}
