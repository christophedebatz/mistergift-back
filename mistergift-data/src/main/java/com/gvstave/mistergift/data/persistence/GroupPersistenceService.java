package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Group;
import com.gvstave.mistergift.data.domain.QGroup;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.GroupRepository;
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
 * Repository for {@link Group}.
 */
@Repository
public class GroupPersistenceService extends BaseQueryDslRepositorySupport<Group> implements GroupRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    protected GroupPersistenceService() {
        super(Group.class);
    }

    /**
     * Saves a {@link Group}.
     *
     * @param group The group to persist.
     * @return The hydrated user.
     */
    @Transactional
    public <S extends Group> S save(S group) {
        Objects.requireNonNull(group);
        S newGroup = getEntityManager().merge(group);
        getEntityManager().flush();
        return newGroup;
    }

    public <S extends Group> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    public Group findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Group> findAll() {
        return null;
    }

    public Iterable<Group> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(Group group) {

    }

    public void delete(Iterable<? extends Group> iterable) {

    }

    public void deleteAll() {

    }

    public Group findOne(Predicate predicate) {
        return null;
    }

    public Iterable<Group> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<Group> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<Group> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<Group> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<Group> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

    public Iterable<Group> findAll(Sort sort) {
        return null;
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Group> findAll(Pageable pageable) {
        JPQLQuery query = from(QGroup.group);
        long resultsCount = query.count();
        return buildPage(resultsCount, applyPagination(query, pageable).list(QGroup.group), pageable);
    }
}
