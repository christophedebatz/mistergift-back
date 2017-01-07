package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.LandingUser;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LandingUserRepository extends PagingAndSortingRepository<LandingUser, Long>, QueryDslPredicateExecutor<LandingUser> {
}
