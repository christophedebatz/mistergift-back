package com.debatz.mistergift.data.persistence;

import com.debatz.mistergift.data.persistence.repository.UserRepository;
import com.debatz.mistergift.model.User;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class UserPersistenceService implements UserRepository {

    public <S extends User> S save(S s) {
        return null;
    }

    public <S extends User> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    public User findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    public Iterable<User> findAll() {
        return null;
    }

    public Iterable<User> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(User user) {

    }

    public void delete(Iterable<? extends User> iterable) {

    }

    public void deleteAll() {
    }

    public User findOne(Predicate predicate) {
        return null;
    }

    public Iterable<User> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<User> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<User> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<User> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<User> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

    public Iterable<User> findAll(Sort sort) {
        return null;
    }

    public Page<User> findAll(Pageable pageable) {
        return null;
    }
}
