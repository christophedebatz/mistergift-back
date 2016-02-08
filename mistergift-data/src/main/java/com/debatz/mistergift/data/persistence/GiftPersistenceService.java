package com.debatz.mistergift.data.persistence;

import com.debatz.mistergift.data.persistence.repository.GiftRepository;
import com.debatz.mistergift.data.domain.Gift;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class GiftPersistenceService implements GiftRepository {
    public <S extends Gift> S save(S s) {
        return null;
    }

    public Gift findOne(Long aLong) {
        return null;
    }

    public boolean exists(Long aLong) {
        return false;
    }

    public List<Gift> findAll() {
        return null;
    }

    public List<Gift> findAll(Sort sort) {
        return null;
    }

    public Page<Gift> findAll(Pageable pageable) {
        return null;
    }

    public List<Gift> findAll(Iterable<Long> iterable) {
        return null;
    }

    public long count() {
        return 0;
    }

    public void delete(Long aLong) {

    }

    public void delete(Gift gift) {

    }

    public void delete(Iterable<? extends Gift> iterable) {

    }

    public void deleteAll() {

    }

    public void flush() {

    }

    public void deleteInBatch(Iterable<Gift> iterable) {

    }

    public void deleteAllInBatch() {

    }

    public Gift getOne(Long aLong) {
        return null;
    }

    public <S extends Gift> S saveAndFlush(S s) {
        return null;
    }

    public <S extends Gift> List<S> save(Iterable<S> iterable) {
        return null;
    }

    public Gift findOne(Predicate predicate) {
        return null;
    }

    public Iterable<Gift> findAll(Predicate predicate) {
        return null;
    }

    public Iterable<Gift> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    public Iterable<Gift> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Iterable<Gift> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    public Page<Gift> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }
}
