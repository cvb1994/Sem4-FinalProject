package com.personal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "transaction_code", nullable = false)
	private String transactionCode;
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "discount", nullable = true)
	private double discount;
	
	@Column(name = "status_active", nullable = false)
	private Boolean statusActive;
	
	@Column(name = "payment_method")
	private String paymendMethod;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
