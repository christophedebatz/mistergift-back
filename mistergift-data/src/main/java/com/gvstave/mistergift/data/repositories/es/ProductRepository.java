package com.gvstave.mistergift.data.repositories.es;

import com.gvstave.mistergift.data.domain.es.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link Product}.
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

}