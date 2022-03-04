package com.personal.musicplayer.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.SongDto;
import com.personal.entity.Artist;
import com.personal.entity.Genre;
import com.personal.entity.Song;

@Component
public class SongSpecification {
	public Specification<Song> filter(final SongDto criteria){
		return(root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(StringUtils.isNoneBlank(criteria.getTitle())) {
            	predicates.add(cb.like(cb.upper(root.get("title")), "%" + criteria.getTitle().toUpperCase() + "%"));
            }
			
			if(StringUtils.isNoneBlank(criteria.getComposer())) {
            	predicates.add(cb.like(cb.upper(root.get("composer")), "%" + criteria.getComposer().toUpperCase() + "%"));
            }
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
	
	public Specification<Song> filterArtist(final int artistId){
		return(root, query, cb) -> {
			Join<Song, Artist> artistRoot = root.join("artists");
			return cb.equal(artistRoot.get("id"), artistId);
		};
	}
	
	public Specification<Song> filterGenre(final int genreId){
		return(root, query, cb) -> {
			Join<Song, Genre> genreRoot = root.join("genres");
			return cb.equal(genreRoot.get("id"), genreId);
		};
	}
}
