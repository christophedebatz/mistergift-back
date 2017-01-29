package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.BaseEntity;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.BaseEntityRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Objects;

/**
 * Base class for persistence services.
 */
@Service
public class BaseEntityPersistenceService<E extends BaseEntity<ID>, ID extends Serializable> extends BaseQueryDslRepositorySupport<E> {

    /** The entity repository. */
    @Inject
    protected BaseEntityRepository<E, ID> repository;

    /**
     * Default query DSL constructor.
     *
     * @param domainClass The domain class.
     */
    protected BaseEntityPersistenceService(Class<E> domainClass) {
        super(domainClass);
        this.domainClass = domainClass;
    }

    /**
     * Saves a user.
     *
     * @param entity The entity to persist.
     * @return The hydrated entity.
     */
    @Transactional(readOnly = false)
    public E save(E entity) {
        Objects.requireNonNull(entity);
        return repository.save(entity);
    }

    /**
     * Save a list of entities.
     *
     * @param entities The entities to persist.
     * @return The hydrated entities list.
     */
    @Transactional(readOnly = false)
    public Iterable<E> save(Iterable<E> entities) {
        return repository.save(entities);
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id The entity id.
     * @return The entity.
     */
    @Transactional(readOnly = true)
    public E findOne(ID id) {
        return repository.findOne(id);
    }

    /**
     * Returns if an entity exists.
     *
     * @param id The entity id.
     * @return If the entity exists.
     */
    @Transactional(readOnly = true)
    public boolean exists(ID id) {
        return findOne(id) != null;
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a list of entities by their ids.
     *
     * @param ids The entities ids.
     * @return The entities list.
     */
    @Transactional(readOnly = true)
    public Iterable<E> findAll(Iterable<ID> ids) {
        return repository.findAll(ids);
    }

    /**
     * Returns the number of entities.
     *
     * @return The entities count.
     */
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    /**
     * Removes an entity by its id.
     *
     * @param id The entity id.
     */
    @Transactional(readOnly = false)
    public void delete(ID id) {
        repository.delete(id);
    }


    /**
     * Removes an entity.
     *
     * @param entity The entity to remove.
     */
    @Transactional(readOnly = false)
    public void delete(E entity) {
        repository.delete(entity);
    }

    /**
     * Removes a set of users.
     *
     * @param entities The users to remove.
     */
    @Transactional(readOnly = false)
    public void delete(Iterable<E> entities) {
        repository.delete(entities);
    }

    /**
     * Removes all the users. Be careful of this method.
     */
    @Transactional(readOnly = false)
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * Retrieves a user in terms of some predicates.
     *
     * @param predicate The search predicates.
     * @return The user.
     */
    @Transactional(readOnly = true)
    public E findOne(Predicate predicate) {
        return repository.findOne(predicate);
    }

    /**
     * Retrieves a list of users with pagination support.
     *
     * @param pageable The pageable.
     * @return The users.
     */
    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Retrieves a list of users in terms of some predicates
     * and with pagination support.
     *
     * @param predicate The search predicates.
     * @param pageable The pageable.
     * @return The users.
     */
    @Transactional(readOnly = true)
    public Page<E> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    /**
     *
     * @param predicate
     * @return
     */
    @Transactional(readOnly = true)
    public Iterable<E> findAll(Predicate predicate) {
        return repository.findAll(predicate);
    }

    /**
     *
     * @param predicate
     * @param sort
     * @return
     */
    @Transactional(readOnly = true)
    public Iterable<E> findAll(Predicate predicate, Sort sort) {
        return repository.findAll(predicate, sort);
    }

    @Transactional(readOnly = true)
    public Iterable<E> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return repository.findAll(predicate, orderSpecifiers);
    }

    @Transactional(readOnly = true)
    public Iterable<E> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return repository.findAll(orderSpecifiers);
    }

    @Transactional(readOnly = true)
    public long count(Predicate predicate) {
        return repository.count(predicate);
    }

    @Transactional(readOnly = true)
    public boolean exists(Predicate predicate) {
        return repository.exists(predicate);
    }

}
