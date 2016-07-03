package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.LandingUser;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.LandingUserRepository;
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

}
