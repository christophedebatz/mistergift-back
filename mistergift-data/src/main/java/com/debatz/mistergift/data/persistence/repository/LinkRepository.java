package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.data.domain.Link;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long>, QueryDslPredicateExecutor<Link> {
}
