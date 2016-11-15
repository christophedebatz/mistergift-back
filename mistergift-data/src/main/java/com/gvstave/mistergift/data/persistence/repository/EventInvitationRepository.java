package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.EventInvitation;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventInvitationRepository extends PagingAndSortingRepository<EventInvitation, Long>, QueryDslPredicateExecutor<EventInvitation> {
}
