package com.personal.common;

public enum SystemParamEnum {
	USER_IMAGE_DEFAULT("user-image-default"),
	GENRE_IMAGE_DEFAULT("genre-image-default"),
	ARTIST_IMAGE_DEFAULT("artist-image-default"),
	ALBUM_IMAGE_DEFAULT("album-image-default")
	;
	
	public String name;
	
	private SystemParamEnum(String name) {
		this.name = name;
	}
}
