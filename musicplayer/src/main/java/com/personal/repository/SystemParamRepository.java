package com.personal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.entity.SystemParam;

@Repository
public interface SystemParamRepository extends JpaRepository<SystemParam, Integer>{
	Optional<SystemParam> findByParamName(String name);
}
