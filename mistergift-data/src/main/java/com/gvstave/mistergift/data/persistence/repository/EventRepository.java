package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.domain.UserEvent;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository extends PagingAndSortingRepository<Event, Long>, QueryDslPredicateExecutor<Event> {
}
