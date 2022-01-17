package com.personal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.entity.Artist;
import com.personal.entity.Song;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>, JpaSpecificationExecutor<Artist>{
	Optional<Artist> findById(int id);
	Optional<Artist> findByName(String name);

//	@Query(value = "select distinct(a.id), a.*  from songs s Join song_artist sa On s.id = sa.song_id "
//			+ "Join artists a On a.id = sa.artist Order By s.listen_count desc Limit 10" , nativeQuery = true)
//	List<Artist> findTopArtist();
}
