package com.gvstave.mistergift.data.domain.jpa;

import com.gvstave.mistergift.data.domain.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the abstract base entity.
 *
 * @param <ID> the entity type.
 */
@MappedSuperclass
abstract class AbstractJpaBaseEntity<ID extends Serializable> implements BaseEntity<ID>
{

    /** The entity id. */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private ID id;

    /**
     * {@inheritDoc}
     */
    @Override
    public ID getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(ID id) {
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
