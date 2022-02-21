package com.personal.common;

public enum RoleEnum {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN")
	;
	
	public String name;
	private RoleEnum(String name) {
		this.name = name;
	}
}
