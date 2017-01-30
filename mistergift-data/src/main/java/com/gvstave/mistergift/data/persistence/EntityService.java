package com.gvstave.mistergift.data.persistence;

/**
 * .
 */

import com.gvstave.mistergift.data.domain.Entity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * The entity service.
 *
 * @param <E> The entity type.
 * @param <ID> The entity id type.
 */
public interface EntityService<E extends Entity<ID>, ID extends Serializable> {

    /**
     * Returns f object with id exist.
     *
     * @param id The object id.
     * @return If object with id exist.
     */
    boolean exists(ID id);

    /**
     * Saves an object.
     *
     * @param obj The object.
     * @return The saved object.
     */
    E save(E obj);

    /**
     * Saves an iterable of objects.
     *
     * @param obj The objects.
     * @return The saved objects.
     */
    Iterable<E> save(Iterable<E> obj);

    /**
     * Retrieves an object by its id.
     *
     * @param id The object id.
     * @return The object.
     */
    E findOne(ID id);

    /**
     * Retrieves an object using a predicate.
     *
     * @param predicate The predicate.
     * @return The object.
     */
    E findOne(Predicate predicate);

    /**
     * Retrieves all objects.
     *
     * @return The objects.
     */
    Iterable<E> findAll();

    /**
     * Retrieves objects by their ids.
     *
     * @param ids The object ids.
     * @return The objects.
     */
    Iterable<E> findAll(Iterable<ID> ids);

    /**
     * Retrieves all objects matching a predicate.
     *
     * @param predicate The predicate.
     * @return The objects.
     */
    Iterable<E> findAll(Predicate predicate);

    /**
     * Retrieves pagin objects matching a predicate.
     *
     * @param predicate The predicate.
     * @param pageable  The pageable.
     * @return The objects.
     */
    Page<E> findAll(Predicate predicate, Pageable pageable);

    /**
     * Retrieves all objects with pagination support.
     *
     * @param pageable The pageable.
     * @return The objects.
     */
    Page<E> findAll(Pageable pageable);

    /**
     * Retrieves all objects matching a predicate.
     *
     * @param predicate      The predicate.
     * @param orderSpecifier The order specifier to apply.
     * @return The objects.
     */
    Iterable<E> findAll(Predicate predicate, OrderSpecifier<?> orderSpecifier);

    /**
     * Streams all objects.
     *
     * @return The objects.
     */
    Stream<E> streamAll();

    /**
     * Streams all objects matching a predicate.
     *
     * @param predicate The predicate.
     * @return The objects.
     */
    Stream<E> streamAll(Predicate predicate);

    /**
     * Streams all objects matching a predicate.
     *
     * @param predicate      The predicate.
     * @param orderSpecifier The order specifier to apply.
     * @return The objects.
     */
    Stream<E> streamAll(Predicate predicate, OrderSpecifier<?> orderSpecifier);

    /**
     *  Streams all objects matching a predicate.
     *
     * @param predicate The predicate.
     * @param pageable  The pageable.
     * @return The objects.
     */
    Stream<E> streamAll(Predicate predicate, Pageable pageable);

    /**
     * Counts all objects.
     *
     * @return The objects.
     */
    long count();

    /**
     * Counts all objects matching a predicate.
     *
     * @param predicate The predicate.
     * @return The objects.
     */
    long count(Predicate predicate);

    /**
     * Deletes an object.
     *
     * @param id The object id.
     */
    void delete(ID id);

    /**
     * Deletes an entity.
     *
     * @param entity The entity.
     */
    void delete(E entity);

    /**
     * Deletes several entities.
     *
     * @param entities The entities.
     */
    void delete(Iterable<E> entities);

    /**
     * Deletes several entities matching a predicate.
     *
     * @param predicate The predicate.
     */
    void delete(Predicate predicate);

}
