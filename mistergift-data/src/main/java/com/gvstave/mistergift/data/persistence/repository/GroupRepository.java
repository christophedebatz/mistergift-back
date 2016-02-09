package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Group;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GroupRepository extends PagingAndSortingRepository<Group, Long>, QueryDslPredicateExecutor<Group> {
}
