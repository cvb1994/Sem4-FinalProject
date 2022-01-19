package com.personal.common;

public enum UserTypeEnum {
	USER("user"),
	ADMIN("admin")
	;
	
	public String name;
	
	private UserTypeEnum(String name) {
		this.name = name;
	}
}
