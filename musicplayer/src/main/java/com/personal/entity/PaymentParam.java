package com.personal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payment_param")
public class PaymentParam extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "time_expire", nullable = false)
	private int timeExpire;
	
	@Column(name = "unit", nullable = false)
	private String unit;
	
	@Column(name = "discount", nullable = true)
	private double discount;
}
