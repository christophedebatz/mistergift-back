package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Event;
import com.gvstave.mistergift.data.domain.QEvent;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.EventRepository;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
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

    public Page<Event> findAll(Predicate predicate, Pageable pageable) {
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
        JPQLQuery query = from(QEvent.event);
        long resultsCount = query.count();
        return buildPage(resultsCount, applyPagination(query, pageable).list(QEvent.event), pageable);
    }
}
