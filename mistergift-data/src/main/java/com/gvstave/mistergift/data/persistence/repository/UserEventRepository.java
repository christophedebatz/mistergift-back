package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.UserEvent;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserEventRepository extends PagingAndSortingRepository<UserEvent, Long>, QueryDslPredicateExecutor<UserEvent> {
}
