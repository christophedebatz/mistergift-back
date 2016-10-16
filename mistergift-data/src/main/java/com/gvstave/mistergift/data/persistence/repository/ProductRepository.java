package com.gvstave.mistergift.data.persistence.repository;

import com.gvstave.mistergift.data.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

}