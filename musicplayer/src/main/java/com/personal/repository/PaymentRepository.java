package com.personal.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>, JpaSpecificationExecutor<Payment>{
	Optional<Payment> findByTxnCode(String code);
	List<Payment> findByCreatedDateBetweenAndTransCompletedTrue(LocalDateTime start, LocalDateTime end);
	int countByCreatedDateBetweenAndTransCompletedTrue(LocalDateTime start, LocalDateTime end);
	
}
