package com.personal.musicplayer.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.AlbumDto;
import com.personal.entity.Album;
import com.personal.entity.Artist;
import com.personal.entity.Song;

@Component
public class AlbumSpecification {
	public Specification<Album> filter(final AlbumDto criteria){
		return(root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(StringUtils.isNoneBlank(criteria.getName())) {
            	predicates.add(cb.like(cb.upper(root.get("name")), "%" + criteria.getName().toUpperCase() + "%"));
            }
			
			if(criteria.getArtistId() != 0) {
				Join<Album, Artist> artistRoot = root.join("artist");
            	predicates.add(cb.equal(artistRoot.get("id"), criteria.getArtistId()));
            }
			
			predicates.add(cb.equal(root.get("deleted"), false));
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
}
