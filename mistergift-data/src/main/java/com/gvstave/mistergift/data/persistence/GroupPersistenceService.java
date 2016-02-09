package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.persistence.repository.GroupRepository;
import com.gvstave.mistergift.data.domain.Group;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class GroupPersistenceService implements GroupRepository {

    public <S extends Group> S save(S s) {
        return null;
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

    public Page<Group> findAll(Pageable pageable) {
        return null;
    }
}
