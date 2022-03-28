package com.personal.repository;

import com.personal.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist>{
	Optional<Artist> findByIdAndDeletedFalse(int id);
	Optional<Artist> findByNameAndDeletedFalse(String name);
	List<Artist> findTop10ByOrderByModifiedDateDesc();
	int countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
	List<Artist> findByNameContaining(String name);

}
