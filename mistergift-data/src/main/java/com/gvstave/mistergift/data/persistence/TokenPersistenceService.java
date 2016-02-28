package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Token;
import com.gvstave.mistergift.data.persistence.repository.TokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.Objects;

@Repository
public class TokenPersistenceService implements TokenRepository {

    /** The entity manager. */
    @PersistenceContext(unitName = "mg-em-default")
    private EntityManager em;

    /**
     *
     * @param token
     * @return
     */
    @Transactional
    public Token save(Token token) {
        Objects.requireNonNull(token);
        Token newToken = em.merge(token);
        em.flush();
        return newToken;
    }

    /**
     *
     * @param token
     * @return
     */
    public Token findOne(String token) {
        return em.find(Token.class, token);
    }

    /**
     *
     * @param token
     * @return
     */
    public boolean exists(String token) {
        return em.find(Token.class, token) != null;
    }

    @Override
    public <S extends Token> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Iterable<Token> findAll() {
        return null;
    }

    @Override
    public Iterable<Token> findAll(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Transactional
    @Override
    public void delete(String id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Token> delete = cb.createCriteriaDelete(Token.class);
        Root userRoot = delete.from(Token.class);
        delete.where(cb.equal(userRoot.get("id"), id));
        em.createQuery(delete).executeUpdate();
    }

    @Override
    public void delete(Token token) {

    }

    @Override
    public void delete(Iterable<? extends Token> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
