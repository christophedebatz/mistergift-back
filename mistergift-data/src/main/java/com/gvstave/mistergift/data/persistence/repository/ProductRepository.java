package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Product;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>, QueryDslPredicateExecutor<Product> {
}
