package com.personal.common;

public enum PaymentTimeUnit {
	DAY("Day"),
	MONTH("Month"),
	YEAR("Year")
	;
	
	public String name;
	private PaymentTimeUnit(String name) {
		this.name = name;
	}
}
