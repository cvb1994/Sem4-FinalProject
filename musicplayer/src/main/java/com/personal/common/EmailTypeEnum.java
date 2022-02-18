package com.personal.common;

public enum EmailTypeEnum {
	WELCOME("welcome"),
	RESET("reset"),
	;
	
	public String name;
	private EmailTypeEnum(String name) {
		this.name = name;
	}
}
