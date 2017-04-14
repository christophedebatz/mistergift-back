package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.service.dto.SearchRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The {@link Product} service.
 */
@Service
public class ProductService {

    /** The max products items. */
    private static final Integer MAX_PRODUCTS_COUNT = 10;

    /** The mongo template. */
    @Inject
    private MongoOperations mongo;

    /**
     * Returns the products by its ids.
     *
     * @param ids The products ids.
     * @param pageable The pageable.
     * @return The products.
     */
    @Transactional(readOnly = true)
    public List<Product> findByIdsIn(final List<String> ids, final Pageable pageable) {
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where(Product.Fields.ID.getName()).in(ids));
        return mongo.find(query, Product.class);
    }

    /**
     * Returns the product by its id.
     *
     * @param id The product id.
     * @return The product.
     */
    @Transactional(readOnly = true)
    public Product findById(final String id) {
        return mongo.findById(id, Product.class);
    }

    /**
     * Returns the last products.
     *
     * @param limit The limit.
     * @return The products.
     */
    @Transactional(readOnly = true)
    public List<Product> getLastProducts(Integer limit, Date since) {
        if (limit == null) {
            limit = MAX_PRODUCTS_COUNT;
        }
        final Query query = new Query().with(new PageRequest(1, limit, Sort.Direction.DESC));
        if (since != null) {
            query.addCriteria(Criteria.where(Product.Fields.DATE.getName()).gte(since));
        }
        return mongo.find(query, Product.class);
    }

    /**
     * Returns the product by search:
     * - by brand
     * - by name
     * - by description
     *
     * @param search The search.
     * @param pageable The pageable.
     * @return The results.
     */
    @Transactional(readOnly = true)
    public List<Product> search(final String search, final Pageable pageable) {
        final Query query = new Query().with(pageable);
        //@formatter:off
        Criteria criteria = Criteria.where(Product.Fields.BRAND.getName()).regex(search)
                .orOperator(Criteria.where(Product.Fields.DESCRIPTION.getName()).regex(search)
                        .orOperator(Criteria.where(Product.Fields.NAME.getName()).regex(search)));
        //@formatter:on
        query.addCriteria(criteria);
        return mongo.find(query, Product.class);
    }

    /**
     * Returns the product by search (this is an advanced search), by:
     * - by brand
     * - by name
     * - by description
     * - by reference
     *
     * @param search The search.
     * @param pageable The pageable.
     * @return The results.
     */
    @Transactional(readOnly = true)
    public List<Product> search(final SearchRequestDto search, final Pageable pageable) {
        final Query query = new Query().with(pageable);
        //@formatter:off
        Criteria criteria = new Criteria();
        Optional.ofNullable(search.getName())
                .ifPresent(name -> criteria.orOperator(Criteria.where(Product.Fields.NAME.getName()).regex(name)));
        Optional.ofNullable(search.getBrand())
                .ifPresent(brand -> criteria.orOperator(Criteria.where(Product.Fields.BRAND.getName()).regex(brand)));
        Optional.ofNullable(search.getDescription())
                .ifPresent(description -> criteria.orOperator(Criteria.where(Product.Fields.DESCRIPTION.getName()).regex(description)));
        Optional.ofNullable(search.getReference())
                .ifPresent(reference -> criteria.orOperator(Criteria.where(Product.Fields.REFERENCE.getName()).is(reference)));
        //@formatter:on
        query.addCriteria(criteria);
        return mongo.find(query, Product.class);
    }

}
