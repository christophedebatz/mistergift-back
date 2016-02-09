package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Link;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long>, QueryDslPredicateExecutor<Link> {
}
