package com.personal.musicplayer.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.ListenCountDto;
import com.personal.entity.ListenCount;

@Component
public class ListenCountSpecification {
	public Specification<ListenCount> filter(ListenCountDto criteria){
		return(root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(criteria.getMonth() != 0) {
				predicates.add(cb.equal(root.get("month"), criteria.getMonth()));
			}
			
			if(criteria.getYear() != 0) {
				predicates.add(cb.equal(root.get("year"), criteria.getYear()));
			}
			
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}

}
