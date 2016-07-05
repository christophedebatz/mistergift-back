package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.LandingUser;
import com.gvstave.mistergift.data.domain.QLandingUser;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.LandingUserRepository;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
public class LandingUserPersistenceService extends BaseQueryDslRepositorySupport<LandingUser> implements LandingUserRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public LandingUserPersistenceService() {
        super(LandingUser.class);
    }

    /**
     * Saves a file.
     *
     * @param file The user to persist.
     * @return The hydrated file.
     */
    @Transactional
    @Override
    public <S extends LandingUser> S save(S file) {
        Objects.requireNonNull(file);
        S newFile = getEntityManager().merge(file);
        getEntityManager().flush();
        return newFile;
    }

    @Override
    public <S extends LandingUser> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public LandingUser findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<LandingUser> findAll() {
        return null;
    }

    @Override
    public Iterable<LandingUser> findAll(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(LandingUser landingUser) {

    }

    @Override
    public void delete(Iterable<? extends LandingUser> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public LandingUser findOne(Predicate predicate) {
        QLandingUser qUser = QLandingUser.landingUser;
        JPAQuery query = new JPAQuery(getEntityManager());
        return query.from(qUser).where(predicate).uniqueResult(qUser);
    }

    @Override
    public Iterable<LandingUser> findAll(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<LandingUser> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    @Override
    public Iterable<LandingUser> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Iterable<LandingUser> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Page<LandingUser> findAll(Predicate predicate, Pageable pageable
    ) {
        return null;
    }

    @Override
    public long count(Predicate predicate) {
        return 0;
    }

    @Override
    public boolean exists(Predicate predicate) {
        return false;
    }
}
