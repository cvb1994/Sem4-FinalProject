package com.personal.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private Boolean transCompleted;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate expireDate;
	
	private UserDto user;
	
	private int userId;
	private int paymentParamId;
	
	private String orderType;
	private String orderInfo;
	private String bankCode;
	
}
