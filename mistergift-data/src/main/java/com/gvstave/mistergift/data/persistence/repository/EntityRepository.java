package com.gvstave.mistergift.data.persistence.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Base entity repository.
 */
@NoRepositoryBean
public interface EntityRepository<E extends Serializable, ID extends Serializable> extends PagingAndSortingRepository<E, ID>, QueryDslPredicateExecutor<E> {
}
