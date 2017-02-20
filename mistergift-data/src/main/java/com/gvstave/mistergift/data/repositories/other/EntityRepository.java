package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.BaseEntity;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * JPA Base entity repository.
 */
@NoRepositoryBean
interface EntityRepository<E extends BaseEntity<ID>, ID extends Serializable> extends PagingAndSortingRepository<E, ID>, QueryDslPredicateExecutor<E> {
}
