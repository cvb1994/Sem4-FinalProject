package com.personal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.entity.PlayList;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Integer>, JpaSpecificationExecutor<PlayList>{
	@Query("from PlayList pl where pl.user.id =:userId")
	List<PlayList> findByUserAndDeletedFalse(int userId);
	
	Optional<PlayList> findByUserIdAndNameAndDeletedFalse(int userId, String name);

}
