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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personal.entity.Artist;
import com.personal.entity.Genre;
import com.personal.entity.Song;

import javax.transaction.Transactional;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer>, JpaSpecificationExecutor<Song>{
//	@Query(value = "Select s.* From songs s Where s.id = :songId And s.deleted = false" , nativeQuery = true)
//	Song findBySongId(@Param("songId") int id);
	Optional<Song> findByTitle(String title);
	List<Song> findTop10ByOrderByListenCountResetDesc();
	Page<Song> findSongByArtists_Id(int artistId, Pageable pageable);
	Page<Song> findSongByAlbumId(int albumId, Pageable pageable);
	Page<Song> findSongByGenres_Id(int genreId, Pageable pageable);

	@Modifying
	@Transactional
	@Query("update Song s set s.listenCountReset = 0 ")
	void resetListTrending();
	
	int countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

//	
//	List<Song> findAllByAlbum_id(int albumId);
//	List<Song> findBySongArtist(Artist artist);
//	List<Song> findTop4ByOrderByCreatedDateDesc();
//	List<Song> findTop4ByOrderByListenCountDesc();
	
//	@Query(value = "Select s.* From songs s Inner Join song_genre sg On s.id = sg.song_id Where sg.genre_id = :genreId" , nativeQuery = true)
//	List<Song> findAllByGenre(@Param("genreId") int id);
	
//	List<Song> findTop10ByGenreOrderByListenCountReset(Genre genre);
}
