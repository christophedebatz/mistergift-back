package com.gvstave.mistergift.data.repositories.other;

import com.gvstave.mistergift.data.domain.BaseEntity;
import com.gvstave.mistergift.data.utils.Streams;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * The abstract entity service.
 *
 * @param <E> The entity type.
 * @param <ID> The entity id type.
 */
class EntityPersistenceService<E extends BaseEntity<ID>, ID extends Serializable> implements EntityService<E, ID> {

    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityPersistenceService.class);

    /** The repository. */
    @Inject
    private EntityRepository<E, ID> repository;

    /** The type. */
    private String type;

    /**
     * Sets the type.
     */
    @PostConstruct
    void postConstruct() {
        type = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getName();
        type = type.substring(type.lastIndexOf('.') + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(ID id) {
        Objects.requireNonNull(id);
        LOGGER.info("Checking existence of {} with id {}", type, id);
        return repository.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public E save(E entity) {
        Objects.requireNonNull(entity);
        LOGGER.info("Saving {}={}", type, entity);
        return repository.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Iterable<E> save(Iterable<E> entities) {
        Objects.requireNonNull(entities);
        LOGGER.info("Saving {}={}", type, entities);
        return repository.save(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public E findOne(ID id) {
        Objects.requireNonNull(id);
        LOGGER.info("Retrieving {} with id={}", type, id);
        return repository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public E findOne(Predicate predicate) {
        Objects.requireNonNull(predicate);
        LOGGER.info("Retrieving {} with predicate={}", type, predicate);
        return repository.findOne(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        LOGGER.info("Retrieving all {}", type);
        return repository.findAll();
    }

    /**
     * Retrieves objects by their ids.
     *
     * @param ids The object ids.
     * @return The objects.
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll(Iterable<ID> ids) {
        Objects.requireNonNull(ids);
        LOGGER.info("Retrieving {} with ids={}", type, ids);
        return repository.findAll(ids);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll(Predicate predicate) {
        Objects.requireNonNull(predicate);
        LOGGER.info("Retrieving {} with predicate={}", type, predicate);
        return repository.findAll(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<E> findAll(Predicate predicate, Pageable pageable) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(pageable);
        LOGGER.info("Retrieving {} with predicate={} and pageable={}", type, predicate, pageable);
        return repository.findAll(predicate, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        Objects.requireNonNull(pageable);
        LOGGER.info("Retrieving all {} with pageable={}", type, pageable);
        return repository.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll(Predicate predicate, OrderSpecifier<?> orderSpecifier) {
        Objects.requireNonNull(predicate);
        LOGGER.info("Retrieving {} with predicate={} and orderSpecifier={}", type, predicate, orderSpecifier);
        return repository.findAll(predicate, orderSpecifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Stream<E> streamAll() {
        return Streams.of(findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Stream<E> streamAll(Predicate predicate) {
        return Streams.of(findAll(predicate));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Stream<E> streamAll(Predicate predicate, Pageable pageable) {
        return Streams.of(findAll(predicate, pageable));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Stream<E> streamAll(Predicate predicate, OrderSpecifier<?> orderSpecifier) {
        return Streams.of(findAll(predicate, orderSpecifier));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        LOGGER.info("Counting all {}", type);
        return repository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long count(Predicate predicate) {
        Objects.requireNonNull(predicate);
        LOGGER.info("Counting {} with predicate={}", type, predicate);
        return repository.count(predicate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(ID id) {
        Objects.requireNonNull(id);
        LOGGER.info("Deleting {} with id={}", type, id);
        repository.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(E entity) {
        Objects.requireNonNull(entity);
        LOGGER.info("Deleting {}={}", type, entity);
        repository.delete(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Iterable<E> entities) {
        Objects.requireNonNull(entities);
        LOGGER.info("Deleting {}={}", type, entities);
        repository.delete(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Predicate predicate) {
        Objects.requireNonNull(predicate);
        LOGGER.info("Deleting {} with predicate={}", type, predicate);
        Iterable<E> entitiesToDelete = repository.findAll(predicate);
        repository.delete(entitiesToDelete);
    }

    /**
     * Returns the repository.
     *
     * @return The repository.
     */
    protected final EntityRepository<E, ID> getRepository() {
        return repository;
    }

    /**
     * Returns the current entity type.
     *
     * @return The current entity type.
     */
    protected final String getType() {
        return type;
    }

}
