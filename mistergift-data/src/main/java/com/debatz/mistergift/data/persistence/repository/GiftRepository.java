package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.model.Gift;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GiftRepository extends PagingAndSortingRepository<Gift, Long>, QueryDslPredicateExecutor<Gift> {
}
