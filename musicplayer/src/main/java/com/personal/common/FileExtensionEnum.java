package com.personal.common;

public enum FileExtensionEnum {
	AUDIO("audio"),
	IMAGE("image")
	
	;
	
	public String name;
	private FileExtensionEnum(String name) {
		this.name = name;
	}
}
