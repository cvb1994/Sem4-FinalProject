package com.personal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personal.entity.ListenCount;

@Repository
public interface ListenCountRepository extends JpaRepository<ListenCount, Integer>{
	
}
