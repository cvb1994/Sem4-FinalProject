package com.personal.musicplayer.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.ArtistDto;
import com.personal.entity.Artist;
import com.personal.entity.Song;

@Component
public class ArtistSpecification {
	public Specification<Artist> filter(final ArtistDto criteria ){
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if(StringUtils.isNoneBlank(criteria.getName())) {
            	predicates.add(cb.like(cb.upper(root.get("name")), "%" + criteria.getName().toUpperCase() + "%"));
            }
			
			if(StringUtils.isNoneBlank(criteria.getGender())) {
            	predicates.add(cb.like(cb.upper(root.get("gender")), criteria.getGender().toUpperCase()));
            }
			
			if(StringUtils.isNoneBlank(criteria.getNationality())) {
            	predicates.add(cb.like(cb.upper(root.get("nationality")), "%" + criteria.getNationality().toUpperCase() + "%"));
            }
			predicates.add(cb.equal(root.get("deleted"), false));
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
	
	public Specification<Artist> filterTop(){
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			Join<Artist, Song> songRoot = root.join("songs");
			query.distinct(true);
			
			predicates.add(cb.equal(root.get("deleted"), false));
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
}
