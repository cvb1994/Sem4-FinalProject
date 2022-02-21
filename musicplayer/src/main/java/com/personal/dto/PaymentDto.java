package com.personal.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentDto extends BaseDto{
	private int id;
	private String transactionCode;
	private String txnCode;
	private int price;
	private double discount;
	private String paymentMethod;
	private Boolean statusActive;
	private UserDto user;
	private LocalDate expireDate;
	
	private int userId;
	private int paymentParamId;
	
	private String orderType;
	private String orderInfo;
	private String bankCode;
	
}
