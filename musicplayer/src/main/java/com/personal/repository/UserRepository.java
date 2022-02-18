package com.personal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
	Optional<User> findById(int id);
	Optional<User> findByUsername(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameAndIdNot(String name, int id);
	List<User> findByEmailOrPhone(String email, String phone);
}
