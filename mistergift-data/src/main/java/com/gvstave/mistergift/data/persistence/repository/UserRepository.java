package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.User;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends CrudRepository<User, Long>, QueryDslPredicateExecutor<User> {

}