package com.gvstave.mistergift.data.persistence;

import com.google.common.collect.Lists;
import com.gvstave.mistergift.data.domain.QUser;
import com.gvstave.mistergift.data.domain.User;
import com.gvstave.mistergift.data.persistence.querydsl.BaseQueryDslRepositorySupport;
import com.gvstave.mistergift.data.persistence.repository.UserRepository;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserPersistenceService extends BaseQueryDslRepositorySupport<User> implements UserRepository {

    /**
     * The default constructor for QueryDsl support.
     */
    public UserPersistenceService() {
        super(User.class);
    }

    /**
     * Saves a user.
     *
     * @param user The user to persist.
     * @return The hydrated user.
     */
    @Transactional
    public <S extends User> S save(S user) {
        Objects.requireNonNull(user);
        S newUser = getEntityManager().merge(user);
        getEntityManager().flush();
        return newUser;
    }

    /**
     * Save a list of user.
     *
     * @param users The users to persist.
     * @return The hydrated users list.
     */
    public <S extends User> Iterable<S> save(Iterable<S> users) {
        List<S> results = new ArrayList<>();
        for (S user : users) {
            results.add(save(user));
        }
        return results;
    }

    /**
     * Retrieves a user by its id.
     *
     * @param id The user id.
     * @return The user.
     */
    public User findOne(Long id) {
        return getEntityManager().find(User.class, id);
    }

    /**
     * Returns wether a user exists.
     *
     * @param id The user id.
     * @return If the user exists.
     */
    public boolean exists(Long id) {
        return findOne(id) != null;
    }

    public Iterable<User> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> allQuery = getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }

    /**
     * Retrieves a list of users by their ids.
     *
     * @param ids The users ids.
     * @return The users list.
     */
    public Iterable<User> findAll(Iterable<Long> ids) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        CriteriaQuery<User> byId = cq.select(userRoot).where(cb.equal(userRoot.get("id"), ids));
        TypedQuery<User> allQuery = getEntityManager().createQuery(byId);
        return allQuery.getResultList();
    }

    /**
     * Returns the number of the users.
     *
     * @return The users count.
     */
    public long count() {
        List<User> users = Lists.newArrayList(findAll());
        return users.size();
    }

    /**
     * Removes a user by its id.
     *
     * @param id The user id.
     */
    public void delete(Long id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
        Root userRoot = delete.from(User.class);
        delete.where(cb.equal(userRoot.get("id"), id));
        getEntityManager().createQuery(delete).executeUpdate();
    }


    /**
     * Removes a user.
     *
     * @param user The user to remove.
     */
    public void delete(User user) {
        getEntityManager().remove(user);
    }

    /**
     * Removes a set of users.
     *
     * @param users The users to remove.
     */
    public void delete(Iterable<? extends User> users) {
        for (User user : users) {
            delete(user);
        }
    }

    /**
     * Removes all the users. Be careful of this method.
     */
    public void deleteAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
        getEntityManager().createQuery(delete).executeUpdate();
    }

    /**
     * Retrieves a user by its email address.
     *
     * @param email The user email address.
     * @return The user.
     */
    public User findByEmail(String email) {
        Objects.requireNonNull(email);
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        CriteriaQuery<User> byEmail = cq.select(user).where(cb.equal(user.get("email"), email));
        TypedQuery<User> allQuery = getEntityManager().createQuery(byEmail);
        return allQuery.getSingleResult();
    }

    /**
     * Retrieves a user in terms of some predicates.
     *
     * @param predicate The search predicates.
     * @return The user.
     */
    public User findOne(Predicate predicate) {
        QUser qUser = QUser.user;
        JPAQuery query = new JPAQuery(getEntityManager());
        return query.from(qUser).where(predicate).uniqueResult(qUser);
    }

    /**
     * Retrieves a list of users with pagination support.
     *
     * @param pageable The pageable.
     * @return The users.
     */
    public Page<User> findAll(Pageable pageable) {
        JPQLQuery query = from(QUser.user);
        long resultsCount = query.count();
        return buildPage(resultsCount, applyPagination(query, pageable).list(QUser.user), pageable);
    }

    /**
     * Retrieves a list of users in terms of some predicates
     * and with pagination support.
     *
     * @param predicate The search predicates.
     * @param pageable The pageable.
     * @return The users.
     */
    public Page<User> findAll(Predicate predicate, Pageable pageable) {
        JPQLQuery query = from(QUser.user).where(predicate);
        long resultsCount = query.count();
        return buildPage(resultsCount, applyPagination(query, pageable)
                .list(QUser.user), pageable);
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

    public long count(Predicate predicate) {
        return 0;
    }

    public boolean exists(Predicate predicate) {
        return false;
    }

}
