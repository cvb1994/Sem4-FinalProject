package com.personal.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.personal.entity.Song;

import javax.transaction.Transactional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer>, JpaSpecificationExecutor<Song>{
//	@Query(value = "Select s.* From songs s Where s.id = :songId And s.deleted = false" , nativeQuery = true)
//	Song findBySongId(@Param("songId") int id);
	Optional<Song> findByTitle(String title);
	List<Song> findTop20ByOrderByListenCountResetDesc();
	List<Song> findTop15ByOrderByListenCountResetDesc();
	Page<Song> findSongByArtists_IdAndDeletedFalse(int artistId, Pageable pageable);
	Page<Song> findSongByAlbumIdAndDeletedFalse(int albumId, Pageable pageable);
	Page<Song> findSongByGenres_IdAndDeletedFalse(int genreId, Pageable pageable);

	@Modifying
	@Transactional
	@Query("update Song s set s.listenCountReset = 0 ")
	void resetListTrending();
	
	List<Song> findByTitleContaining(String title);
	
	int countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

	List<Song> findTop10ByOrderByCreatedDateDesc();
	
//	@Query(value = "Select s.* From songs s Inner Join song_genre sg On s.id = sg.song_id Where sg.genre_id = :genreId" , nativeQuery = true)
//	List<Song> findAllByGenre(@Param("genreId") int id);

}
