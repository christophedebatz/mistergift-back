package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Product;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.ProductRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
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

    /**
     *
     * @param predicate
     * @param pageable
     * @return
     */
    public Page<Product> findAll(Predicate predicate, Pageable pageable) {
        return new PageImpl<Product>(new ArrayList<>());
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

    @Override
    public <S extends Product> S index (S s) {
        return null;
    }

    @Override
    public Iterable<Product> search (QueryBuilder queryBuilder) {
        return null;
    }

    @Override
    public Page<Product> search (QueryBuilder queryBuilder, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Product> search (SearchQuery searchQuery) {
        return null;
    }

    @Override
    public Page<Product> searchSimilar (Product product, String[] strings, Pageable pageable
    ) {
        return null;
    }

    @Override
    public void refresh () {

    }
}
