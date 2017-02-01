package com.gvstave.mistergift.data.persistence.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.io.Serializable;

/**
 * .
 */
public interface MongoEntityRepository <E extends Serializable, ID extends Serializable> extends MongoRepository<E, ID>, QueryDslPredicateExecutor<E> {
}
