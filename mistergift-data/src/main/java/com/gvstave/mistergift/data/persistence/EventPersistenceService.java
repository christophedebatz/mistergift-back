package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.domain.QEvent;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.EventRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Repository for {@link Event}.
 */
@Repository
public class EventPersistenceService extends BaseQueryDslRepositorySupport<Event> implements EventRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    protected EventPersistenceService() {
        super(Event.class);
    }

    /**
     * Saves a {@link Event}.
     *
     * @param group The event to persist.
     * @return The hydrated user.
     */
    @Transactional
    public <S extends Event> S save(S group) {
        Objects.requireNonNull(group);
        S newGroup = getEntityManager().merge(group);
        getEntityManager().flush();
        return newGroup;
    }

    @Transactional
    public <S extends Event> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    /**
     *
     * @param aLong
     * @return
     */
    public Event findOne(Long aLong) {
        Objects.requireNonNull(aLong);
        return getEntityManager().find(Event.class, aLong);
    }

    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Event> findAll() {
        return null;
    }

    public Iterable<Event> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    /**
     * Deletes a event.
     *
     * @param id The event id.
     */
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id);
        Event event = getEntityManager().find(Event.class, id);
        getEntityManager().remove(event);
    }

    public void delete(Event event) {

    }

    public void delete(Iterable<? extends Event> iterable) {

    }

    public void deleteAll() {

    }

    public Event findOne(Predicate predicate) {
        return null;
    }

    public Iterable<Event> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<Event> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<Event> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<Event> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

    public Iterable<Event> findAll(Sort sort) {
        return null;
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Event> findAll(Pageable pageable) {
        JPAQuery query = new JPAQuery<>(getEntityManager())
            .from(QEvent.event);
        long resultsCount = query.fetchCount();
        return buildPage(resultsCount, applyPagination(query, pageable), pageable);
    }

    /**
     *
     * @param predicate
     * @param pageable
     * @return
     */
    public Page<Event> findAll(Predicate predicate, Pageable pageable) {
        JPAQuery query = new JPAQuery<>(getEntityManager())
            .from(QEvent.event)
            .where(predicate);
        long resultsCount = query.fetchCount();
        return buildPage(resultsCount, applyPagination(query, pageable), pageable);
    }

}
