package com.gvstave.mistergift.data.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the abstract base entity.
 *
 * @param <T> the entity type.
 */
@MappedSuperclass
abstract class AbstractJpaEntity<T extends Serializable> implements BaseEntity<T>
{

    /** The entity id. */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private T id;

    /**
     * {@inheritDoc}
     */
    @Override
    public T getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(T id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        return super.equals(object)
                && object instanceof BaseEntity
                && Objects.equals(((BaseEntity) object).getId(), id);
    }

}