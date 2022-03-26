package com.personal.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{
	Optional<User> findByIdAAndDeletedFalse(int id);
	Optional<User> findByUsername(String name);
	Optional<User> findByUsernameAndDeletedFalse(String name);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameAndIdNot(String name, int id);
	Optional<User> findByEmailAndIdNot(String email, int id);
	int countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
}
