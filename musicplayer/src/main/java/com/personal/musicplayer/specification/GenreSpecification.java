package com.personal.musicplayer.specification;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.GenreDto;
import com.personal.entity.Genre;

@Component
public class GenreSpecification {
	public Specification<Genre> filter(final GenreDto criteria ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if(StringUtils.isNoneBlank(criteria.getName())) {
            	predicates.add(cb.like(cb.upper(root.get("name")), "%" + criteria.getName().toUpperCase() + "%"));
            }
            predicates.add(cb.equal(root.get("deleted"), false));
            
            return cb.and(predicates.stream().toArray(Predicate[]::new));
        };

    }
}
