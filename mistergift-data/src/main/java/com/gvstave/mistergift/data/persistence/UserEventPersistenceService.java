package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.QUserEvent;
import com.gvstave.mistergift.data.domain.UserEvent;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.UserEventRepository;
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
 * Repository for {@link UserEvent}.
 */
@Repository
public class UserEventPersistenceService extends BaseQueryDslRepositorySupport<UserEvent> implements UserEventRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    protected UserEventPersistenceService() {
        super(UserEvent.class);
    }

    /**
     * Saves a {@link UserEvent}.
     *
     * @param userEvent The event to persist.
     * @return The hydrated user.
     */
    @Transactional
    public <S extends UserEvent> S save(S userEvent) {
        Objects.requireNonNull(userEvent);
        S newGroup = getEntityManager().merge(userEvent);
        getEntityManager().flush();
        return newGroup;
    }

    public <S extends UserEvent> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    /**
     *
     * @param aLong
     * @return
     */
    public UserEvent findOne(Long aLong) {
        Objects.requireNonNull(aLong);
        return getEntityManager().find(UserEvent.class, aLong);
    }

    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<UserEvent> findAll() {
        return null;
    }

    public Iterable<UserEvent> findAll(Iterable<Long> iterable) {
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
        UserEvent event = getEntityManager().find(UserEvent.class, id);
        getEntityManager().remove(event);
    }

    public void delete(UserEvent event) {

    }

    public void delete(Iterable<? extends UserEvent> iterable) {

    }

    public void deleteAll() {

    }

    public UserEvent findOne(Predicate predicate) {
        return null;
    }

    public Iterable<UserEvent> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<UserEvent> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<UserEvent> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<UserEvent> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<UserEvent> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

    public Iterable<UserEvent> findAll(Sort sort) {
        return null;
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<UserEvent> findAll(Pageable pageable) {
        JPAQuery query = new JPAQuery<UserEvent>(getEntityManager())
            .from(QUserEvent.userEvent);
        long resultsCount = query.fetchCount();
        return buildPage(resultsCount, applyPagination(query, pageable), pageable);
    }
}
