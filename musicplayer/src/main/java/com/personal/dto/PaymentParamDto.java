package com.personal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentParamDto extends BaseDto{
	private int id;
	private int price;
	private int actualPrice;
	private int timeExpire;
	private double discount;
	private String unit;
	
	private boolean isSelect = false;
}
