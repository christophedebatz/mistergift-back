package com.gvstave.mistergift.data.persistence.jpa.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.io.Serializable;

/**
 * JPA Base entity repository.
 */
public interface JpaEntityRepository<E extends Serializable, ID extends Serializable> extends JpaRepository<E, ID>, QueryDslPredicateExecutor<E> {
}
