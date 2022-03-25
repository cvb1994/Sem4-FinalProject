package com.personal.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.dto.GenreReportDto;
import com.personal.entity.Genre;
import com.personal.entity.Song;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer>, JpaSpecificationExecutor<Genre>{
	Optional<Genre> findById(int id);
	Optional<Genre> findByName(String name);
	
	Optional<Genre> findByNameIgnoreCase(String name);
	Optional<Genre> findByNameIgnoreCaseAndIdNot(String name, int id);
	
//	List<Genre> findAllByGenreSong(Song song);
	
//	@Query(value = "select distinct(g.id), g.*  from songs s Join song_genre sg On s.id = sg.song_id "
//			+ "Join genres g On g.id = sg.genre_id Order By s.listen_count desc Limit 6" , nativeQuery = true)
//	List<Genre> findTopGenre();
	
}
