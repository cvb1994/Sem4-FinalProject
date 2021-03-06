package com.personal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	Optional<Admin> findByUsername(String username);
	Optional<Admin> findByUsernameAndIdNot(String username, int id);
}
