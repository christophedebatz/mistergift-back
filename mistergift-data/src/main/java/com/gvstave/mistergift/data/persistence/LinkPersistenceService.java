package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Link;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.LinkRepository;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class LinkPersistenceService extends BaseQueryDslRepositorySupport<Link> implements LinkRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public LinkPersistenceService() {
        super(Link.class);
    }

    public Iterable<Link> findAll(Sort sort) {
        return null;
    }

    public Page<Link> findAll(Pageable pageable) {
        return null;
    }

    public <S extends Link> S save(S s) {
        return null;
    }

    public <S extends Link> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    public Link findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    public Iterable<Link> findAll() {
        return null;
    }

    public Iterable<Link> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(Link link) {

    }

    public void delete(Iterable<? extends Link> iterable) {

    }

    public void deleteAll() {

    }

    public Link findOne(Predicate predicate) {
        return null;
    }

    public Iterable<Link> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<Link> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<Link> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<Link> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<Link> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }
}
