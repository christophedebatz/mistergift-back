package com.gvstave.mistergift.data.domain.es;

import com.gvstave.mistergift.data.domain.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the abstract base entity.
 *
 * @param <T> the entity type.
 */
@NoRepositoryBean
abstract class AbstractElasticsearchBaseEntity<T extends Serializable> implements BaseEntity<T>
{

    /** The entity id. */
    @Id
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
