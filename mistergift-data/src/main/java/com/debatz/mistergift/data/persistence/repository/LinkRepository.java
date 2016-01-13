package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.model.Link;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LinkRepository extends PagingAndSortingRepository<Link, Long>, QueryDslPredicateExecutor<Link> {
}
