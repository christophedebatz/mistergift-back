package com.debatz.mistergift.data.persistence;

import com.debatz.mistergift.data.domain.Token;
import org.springframework.stereotype.Repository;

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
