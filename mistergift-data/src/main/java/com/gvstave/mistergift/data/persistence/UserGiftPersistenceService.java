package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.UserGift;
import com.gvstave.mistergift.data.domain.UserGiftId;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.UserGiftRepository;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
public class UserGiftPersistenceService extends BaseQueryDslRepositorySupport<UserGift> implements UserGiftRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public UserGiftPersistenceService () {
        super(UserGift.class);
    }

    /**
     * Saves a file.
     *
     * @param file The user to persist.
     * @return The hydrated file.
     */
    @Transactional
    @Override
    public <S extends UserGift> S save(S file) {
        Objects.requireNonNull(file);
        S newFile = getEntityManager().merge(file);
        getEntityManager().flush();
        return newFile;
    }

    @Override
    public <S extends UserGift> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public UserGift findOne(UserGiftId aLong) {
        return null;
    }

    @Override
    public boolean exists(UserGiftId aLong) {
        return false;
    }

    @Override
    public Iterable<UserGift> findAll() {
        return null;
    }

    @Override
    public Iterable<UserGift> findAll(Iterable<UserGiftId> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(UserGiftId aLong) {

    }

    @Override
    public void delete(UserGift landingUser) {

    }

    @Override
    public void delete(Iterable<? extends UserGift> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public UserGift findOne(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<UserGift> findAll(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<UserGift> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    @Override
    public Iterable<UserGift> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Iterable<UserGift> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Page<UserGift> findAll(Predicate predicate, Pageable pageable
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
