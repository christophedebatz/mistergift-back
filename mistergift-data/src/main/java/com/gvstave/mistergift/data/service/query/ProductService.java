package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.provider.cdiscount.ProductSupplier;
import com.gvstave.mistergift.data.service.dto.ProductDto;
import com.gvstave.mistergift.data.service.dto.SearchRequestDto;
import com.gvstave.mistergift.data.service.dto.mapper.ProductMapper;
import com.gvstave.sdk.cdiscount.domain.RemoteProduct;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@link Product} service.
 */
@Service
public class ProductService {

    /** The max products items. */
    private static final Integer PRODUCT_SEARCH_COUNT = 10;

    @Inject
    private ProductSupplier productSupplier;

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
            limit = PRODUCT_SEARCH_COUNT;
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
    public List<ProductDto> search(final String search, final Pageable pageable) {
        final Query query = new Query().with(pageable);
        //@formatter:off
        Criteria criteria = Criteria.where(Product.Fields.BRAND.getName()).regex(search)
                .orOperator(Criteria.where(Product.Fields.DESCRIPTION.getName()).regex(search)
                        .orOperator(Criteria.where(Product.Fields.NAME.getName()).regex(search)));
        //@formatter:on
        query.addCriteria(criteria);
        final List<Product> products = mongo.find(query, Product.class);
        products.addAll(fetchNewProducts(products, search, pageable));

            return products.stream().map(ProductMapper::map).collect(Collectors.toList());
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
    public List<ProductDto> search(final SearchRequestDto search, final Pageable pageable) {
        final Query query = new Query().with(pageable);
        //@formatter:off
        Criteria criteria = new Criteria();
        Optional.ofNullable(search.getName())
                .ifPresent(name -> {
                    Criteria cName = Criteria.where(Product.Fields.NAME.getName()).regex(name);
                    criteria.andOperator(cName);
                });
        Optional.ofNullable(search.getBrand())
                .ifPresent(brand -> {
                    Criteria cBrand = Criteria.where(Product.Fields.BRAND.getName()).regex(brand);
                    criteria.andOperator(cBrand);
                });
        Optional.ofNullable(search.getDescription())
                .ifPresent(description -> {
                    Criteria cDescription = Criteria.where(Product.Fields.DESCRIPTION.getName()).regex(description);
                    criteria.andOperator(cDescription);
                });
        Optional.ofNullable(search.getReference())
                .ifPresent(reference -> {
                    Criteria cName = Criteria.where(Product.Fields.REFERENCE.getName()).is(reference);
                    criteria.andOperator(cName);
                });
        //@formatter:on
        query.addCriteria(criteria);
        final List<Product> products = mongo.find(query, Product.class);
        products.addAll(fetchNewProducts(products, search.getName(), pageable));

        return products.stream().map(ProductMapper::map).collect(Collectors.toList());
    }

    /**
     * Query products providers with an input search and the already present products names.
     *
     * @param alreadyPresentProducts The already present products names.
     * @param search The input search.
     * @return The new product for this search input.
     */
    private List<Product> fetchNewProducts(final List<Product> alreadyPresentProducts, final String search, final Pageable pageable) {
        List<Product> newProducts = new ArrayList<>();

        if (alreadyPresentProducts.size() < pageable.getPageSize()) {
            PageRequest pageRequest = new PageRequest(0, pageable.getPageSize() - alreadyPresentProducts.size());

            //@formatter:off
            List<String> productsNames = alreadyPresentProducts.stream()
                    .map(Product::getName)
                    .collect(Collectors.toList());

            List<RemoteProduct> remoteProducts = productSupplier.getRemoteProducts(search, productsNames, pageRequest);

            remoteProducts.stream()
                .map(ProductMapper::unmap)
                .forEach(product -> {
                    mongo.save(product);
                    newProducts.add(product);
                });
            //@formatter:on
        }

        return newProducts;
    }

}
