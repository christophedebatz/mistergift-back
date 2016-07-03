package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Product;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.ProductRepository;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPersistenceService extends BaseQueryDslRepositorySupport<Product> implements ProductRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public ProductPersistenceService() {
        super(Product.class);
    }

    public Iterable<Product> findAll(Sort sort) {
        return null;
    }

    public Page<Product> findAll(Pageable pageable) {
        return null;
    }

    public <S extends Product> S save(S s) {
        return null;
    }

    public <S extends Product> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    public Product findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    public Iterable<Product> findAll() {
        return null;
    }

    public Iterable<Product> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(Product link) {

    }

    public void delete(Iterable<? extends Product> iterable) {

    }

    public void deleteAll() {

    }

    public Product findOne(Predicate predicate) {
        return null;
    }

    public Iterable<Product> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<Product> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<Product> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<Product> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<Product> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }
}
