package com.personal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.entity.Payment;
import com.personal.entity.User;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	Optional<Payment> findByTxnCode(String code);
	Optional<Payment> findByUserIdAndStatusActiveTrue(int userId);
	
}
