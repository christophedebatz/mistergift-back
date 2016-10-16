package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Whishlist;
import com.gvstave.mistergift.data.domain.WhishlistId;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.WhishlistRepository;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
public class WhishlistPersistenceService extends BaseQueryDslRepositorySupport<Whishlist> implements WhishlistRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public WhishlistPersistenceService() {
        super(Whishlist.class);
    }

    /**
     * Saves a file.
     *
     * @param file The user to persist.
     * @return The hydrated file.
     */
    @Transactional
    @Override
    public <S extends Whishlist> S save(S file) {
        Objects.requireNonNull(file);
        S newFile = getEntityManager().merge(file);
        getEntityManager().flush();
        return newFile;
    }

    @Override
    public <S extends Whishlist> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Whishlist findOne(WhishlistId aLong) {
        return null;
    }

    @Override
    public boolean exists(WhishlistId aLong) {
        return false;
    }

    @Override
    public Iterable<Whishlist> findAll() {
        return null;
    }

    @Override
    public Iterable<Whishlist> findAll(Iterable<WhishlistId> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(WhishlistId aLong) {

    }

    @Override
    public void delete(Whishlist landingUser) {

    }

    @Override
    public void delete(Iterable<? extends Whishlist> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Whishlist findOne(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<Whishlist> findAll(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<Whishlist> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    @Override
    public Iterable<Whishlist> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Iterable<Whishlist> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Page<Whishlist> findAll(Predicate predicate, Pageable pageable
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
