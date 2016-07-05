package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.LandingUser;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface LandingUserRepository extends CrudRepository<LandingUser, Long>, QueryDslPredicateExecutor<LandingUser> {
}
