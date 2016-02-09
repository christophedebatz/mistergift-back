package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Gift;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GiftRepository extends PagingAndSortingRepository<Gift, Long>, QueryDslPredicateExecutor<Gift> {
}
