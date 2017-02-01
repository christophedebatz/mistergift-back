package com.gvstave.mistergift.data.persistence.mongo.service;

import com.gvstave.mistergift.data.domain.jpa.BaseEntity;
import com.gvstave.mistergift.data.persistence.mongo.repository.MongoEntityRepository;

import javax.inject.Inject;
import java.io.Serializable;

/**
 * .
 */
public class MongoEntityPersistenceService <E extends BaseEntity<ID>, ID extends Serializable> {

    /** The repository. */
    @Inject
    private MongoEntityRepository<E, ID> repository;

}