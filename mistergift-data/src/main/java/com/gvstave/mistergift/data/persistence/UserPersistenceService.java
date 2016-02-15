package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.QUser;
import com.gvstave.mistergift.data.persistence.repository.UserRepository;
import com.gvstave.mistergift.data.domain.User;
import com.google.common.collect.Lists;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserPersistenceService implements UserRepository {

    @PersistenceContext(unitName = "mg-em-default")
    private EntityManager em;

    public <S extends User> S save(S s) {
        Objects.requireNonNull(s);
        em.persist(s);
        em.flush();
        return s;
    }

    public <S extends User> Iterable<S> save(Iterable<S> entities) {
        List<S> results = new ArrayList<S>();
        for (S entity : entities) {
            results.add(save(entity));
        }
        return results;
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public boolean exists(Long id) {
        return findOne(id) != null;
    }

    public Iterable<User> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    public Iterable<User> findAll(Iterable<Long> ids) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        CriteriaQuery<User> byId = cq.select(userRoot).where(cb.equal(userRoot.get("id"), ids));
        TypedQuery<User> allQuery = em.createQuery(byId);
        return allQuery.getResultList();
    }

    public long count() {
        List<User> users = Lists.newArrayList(findAll());
        return users.size();
    }

    public void delete(Long id) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
        Root userRoot = delete.from(User.class);
        delete.where(cb.equal(userRoot.get("id"), id));
        em.createQuery(delete).executeUpdate();
    }

    public void delete(User user) {
        em.remove(user);
    }

    public void delete(Iterable<? extends User> users) {
        for (User user : users) {
            delete(user);
        }
    }

    public void deleteAll() {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaDelete<User> delete = cb.createCriteriaDelete(User.class);
        em.createQuery(delete).executeUpdate();
    }

    public User findByEmail(String email) {
        Objects.requireNonNull(email);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        CriteriaQuery<User> byEmail = cq.select(user).where(cb.equal(user.get("email"), email));
        TypedQuery<User> allQuery = em.createQuery(byEmail);
        return allQuery.getSingleResult();
    }

    public User findOne(Predicate predicate) {
        QUser qUser = QUser.user;
        JPAQuery query = new JPAQuery(em);
        return query.from(qUser).where(predicate).uniqueResult(qUser);
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
}
