package com.personal.common;

public enum FileTypeEnum {
	GENRE_IMAGE("genre"),
	ARTIST_IMAGE("artist"),
	ALBUM_IMAGE("album"),
	SONG_IMAGE("song"),
	USER_IMAGE("user"),
	
	;
	
	public String name;
	private FileTypeEnum(String name) {
		this.name = name;
	}
}
