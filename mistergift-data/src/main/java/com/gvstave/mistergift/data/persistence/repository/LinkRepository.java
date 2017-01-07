package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Link;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LinkRepository extends PagingAndSortingRepository<Link, Long>, QueryDslPredicateExecutor<Link>{
}
