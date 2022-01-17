package com.personal.musicplayer.specification;

import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.GenreDto;
import com.personal.entity.Genre;

@Component
public class GenreSpecification {
	public Specification<Genre> filter(final GenreDto criteria ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            return cb.and(predicates.stream().toArray(Predicate[]::new));
        };

    }
}
