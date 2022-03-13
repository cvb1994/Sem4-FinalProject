package com.personal.common;

public enum FolderTypeEnum {
	GENRE_IMAGE_FOLDER("genre-upload"),
	ARTIST_IMAGE_FOLDER("artist-upload"),
	ALBUM_IMAGE_FOLDER("album-upload"),
	SONG_IMAGE_FOLDER("song-upload"),
	USER_IMAGE_FOLDER("user-upload"),
	AUDIO_FOLDER("music-upload")
	;
	
	public String name;
	
	private FolderTypeEnum(String name) {
		this.name = name;
	}
}
