package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Gift;
import com.gvstave.mistergift.data.domain.QGift;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.GiftRepository;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftPersistenceService extends BaseQueryDslRepositorySupport<Gift> implements GiftRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public GiftPersistenceService() {
        super(Gift.class);
    }

    public <S extends Gift> S save(S s) {
        return null;
    }

    public Gift findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    public List<Gift> findAll() {
        return null;
    }

    public List<Gift> findAll(Sort sort) {
        return null;
    }

    public Page<Gift> findAll(Pageable pageable) {
        return null;
    }

    public List<Gift> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(Gift gift) {

    }

    public void delete(Iterable<? extends Gift> iterable) {

    }

    public void deleteAll() {

    }

    public void flush() {

    }

    public void deleteInBatch(Iterable<Gift> iterable) {

    }

    public void deleteAllInBatch() {

    }

    public Gift getOne(Long aLong) {
        return null;
    }

    public <S extends Gift> S saveAndFlush(S s) {
        return null;
    }

    public <S extends Gift> List<S> save(Iterable<S> iterable) {
        return null;
    }

    public Gift findOne(Predicate predicate) {
        return null;
    }

    public Iterable<Gift> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<Gift> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<Gift> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<Gift> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    /**
     *
     * @param predicate
     * @param pageable
     * @return
     */
    public Page<Gift> findAll(Predicate predicate, Pageable pageable) {
        JPQLQuery query = from(QGift.gift).where(predicate);
        long resultsCount = query.count();
        return buildPage(resultsCount, applyPagination(query, pageable)
                .list(QGift.gift), pageable);
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }
}
