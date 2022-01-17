package com.personal.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentDto extends BaseDto{
	public int id;
	public String transactionCode;
	public int price;
	public double discount;
	public String paymentMethod;
	public Boolean statusActive;
	public UserDto user;
	
}
