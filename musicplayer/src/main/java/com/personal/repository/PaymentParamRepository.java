package com.personal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.entity.PaymentParam;

@Repository
public interface PaymentParamRepository extends JpaRepository<PaymentParam, Integer>{

}
