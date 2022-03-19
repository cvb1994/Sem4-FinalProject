package com.personal.repository;

import com.personal.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist>{
	Optional<Artist> findById(int id);
	Optional<Artist> findByName(String name);
	List<Artist> findTop10ByOrderByModifiedDateDesc();
	int countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

	@Query(value = "select distinct(a.id), a.*  from song s Join song_artist sa On s.id = sa.song_id "
			+ "Join artist a On a.id = sa.artist_id Order By s.listen_count_reset desc Limit 10" , nativeQuery = true)
	List<Artist> findTopArtist();
}
