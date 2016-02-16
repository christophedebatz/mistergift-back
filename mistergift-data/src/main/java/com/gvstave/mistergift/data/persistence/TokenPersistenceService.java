package com.gvstave.mistergift.data.persistence;

import com.gvstave.mistergift.data.domain.Token;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Repository
public class TokenPersistenceService {

    /** The persistence context. */
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
        em.persist(token);
        em.flush();
        return token;
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

}
