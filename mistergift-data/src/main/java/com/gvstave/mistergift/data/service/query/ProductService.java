package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.mongo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * The {@link Product} service.
 */
@Service
public class ProductService {

    @Inject
    private MongoOperations mongoTemplate;

    /**
     *
     * @param ids
     * @param pageable
     * @return
     */
    public List<Product> findByIdsIn(List<String> ids, Pageable pageable) {
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("productId").in(ids));
        return mongoTemplate.find(query, Product.class);
    }

}
