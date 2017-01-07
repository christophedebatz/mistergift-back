package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.EventInvitation;
import com.gvstave.mistergift.data.domain.QEvent;
import com.gvstave.mistergift.data.domain.QEventInvitation;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.EventInvitationRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Repository for {@link EventInvitation}.
 */
@Repository
public class EventInvitationPersistenceService extends BaseQueryDslRepositorySupport<EventInvitation> implements EventInvitationRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    protected EventInvitationPersistenceService () {
        super(EventInvitation.class);
    }

    /**
     * Saves a {@link EventInvitation}.
     *
     * @param group The event to persist.
     * @return The hydrated user.
     */
    @Transactional
    public <S extends EventInvitation> S save(S group) {
        Objects.requireNonNull(group);
        S newGroup = getEntityManager().merge(group);
        getEntityManager().flush();
        return newGroup;
    }

    public <S extends EventInvitation> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    /**
     *
     * @param aLong
     * @return
     */
    public EventInvitation findOne(Long aLong) {
        Objects.requireNonNull(aLong);
        return getEntityManager().find(EventInvitation.class, aLong);
    }

    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<EventInvitation> findAll() {
        return null;
    }

    public Iterable<EventInvitation> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    /**
     * Deletes a invitation.
     *
     * @param id The invitation id.
     */
    @Transactional
    public void delete(Long id) {
        Objects.requireNonNull(id);
        EventInvitation invitation = getEntityManager().find(EventInvitation.class, id);
        getEntityManager().remove(invitation);
    }

    public void delete(EventInvitation event) {

    }

    public void delete(Iterable<? extends EventInvitation> iterable) {

    }

    public void deleteAll() {

    }

    public EventInvitation findOne(Predicate predicate) {
        return null;
    }

    public Iterable<EventInvitation> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<EventInvitation> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<EventInvitation> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<EventInvitation> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

    public Iterable<EventInvitation> findAll(Sort sort) {
        return null;
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<EventInvitation> findAll(Pageable pageable) {
        JPQLQuery query = from(QEvent.event);
        long resultsCount = query.fetchCount();
       // return buildPage(resultsCount, applyPagination(query, pageable).list(QEvent.event), pageable);
        return null;
    }

    /**
     *
     * @param predicate
     * @param pageable
     * @return
     */
    public Page<EventInvitation> findAll(Predicate predicate, Pageable pageable) {
        JPAQuery query = new JPAQuery<EventInvitation>(getEntityManager())
            .from(QEventInvitation.eventInvitation)
            .where(predicate);
        long resultsCount = query.fetchCount();
        return buildPage(resultsCount, applyPagination(query, pageable), pageable);
    }



}
