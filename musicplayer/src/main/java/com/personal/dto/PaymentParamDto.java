package com.personal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentParamDto extends BaseDto{
	public int id;
	public int price;
	public int timeExpire;
	public double discount;
}
