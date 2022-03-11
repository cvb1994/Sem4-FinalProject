package com.personal.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.personal.entity.Album;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer>, JpaSpecificationExecutor<Album>{
	Optional<Album> findById(int id);
	Optional<Album> findByName(String name);
	List<Album> findTop5ByOrderByModifiedDateDesc();
	List<Album> findTop10ByOrderByModifiedDateDesc();
	Page<Album> findByArtistId(int artistId, Pageable pageable);
	int countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);
//	List<Album> findAllByArtist_id(int artistId);
//	
//	List<Album> findTop4ByOrderByCreatedDateDesc();
	
//	@Query(value = "Select DISTINCT (s.album_id), a.* From songs s Inner Join albums a On s.album_id = a.id Order By listen_count Limit 4" , nativeQuery = true)
//	List<Album> findTop4ByListen();
//	
//	@Query(value = "Select DISTINCT (s.album_id), a.* From songs s Inner Join albums a On s.album_id = a.id Order By listen_count Limit 1" , nativeQuery = true)
//	Album findTop1ByListen();
}
