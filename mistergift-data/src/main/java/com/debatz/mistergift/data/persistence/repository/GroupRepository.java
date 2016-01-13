package com.debatz.mistergift.data.persistence.repository;

import com.debatz.mistergift.model.Group;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepository extends PagingAndSortingRepository<Group, Long>, QueryDslPredicateExecutor<Group> {
}