package com.personal.entity;

import java.time.LocalDate;

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
	
	@Column(name = "transaction_code", nullable = true)
	private String transactionCode;							//ma giao dich tai he thong thanh toan
	
	@Column(name = "txnRef_code", nullable = false)
	private String txnCode;									//ma giao dich tai merchant
	
	@Column(name = "price", nullable = false)
	private int price;
	
	@Column(name = "discount", nullable = true)
	private double discount;
	
	@Column(name = "transaction_completed", nullable = false)
	private Boolean transCompleted;
	
	@Column(name = "payment_method")
	private String paymendMethod;
	
	@Column(name = "expire_date", nullable = false)
	private LocalDate expireDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
