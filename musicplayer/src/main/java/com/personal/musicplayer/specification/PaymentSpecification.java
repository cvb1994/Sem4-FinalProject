package com.personal.musicplayer.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.personal.dto.PaymentDto;
import com.personal.entity.Payment;
import com.personal.entity.User;

@Component
public class PaymentSpecification {
	public Specification<Payment> filter(final PaymentDto criteria){
		return(root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(criteria.getUserId() != 0) {
				Join<Payment, User> userRoot = root.join("user");
            	predicates.add(cb.equal(userRoot.get("id"), criteria.getUserId()));
            }
			
			predicates.add(cb.equal(root.get("transCompleted"), true));
			return cb.and(predicates.stream().toArray(Predicate[]::new));
		};
	}
}
