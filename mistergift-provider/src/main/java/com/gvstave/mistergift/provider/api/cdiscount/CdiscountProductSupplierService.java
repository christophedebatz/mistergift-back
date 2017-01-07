package com.gvstave.mistergift.provider.api.cdiscount;

import com.gvstave.mistergift.provider.api.ProductSupplierService;
import com.gvstave.mistergift.provider.api.SearchBuilder;
import com.gvstave.mistergift.provider.api.cdiscount.connector.CdiscountConnector;
import com.gvstave.mistergift.provider.domain.Api;
import com.gvstave.mistergift.provider.domain.RemoteProduct;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
class CdiscountProductSupplierService implements ProductSupplierService {

    /** The environment. */
    @Inject
    private Environment environment;

    /** The CDiscount connector. */
    @Inject
    private CdiscountConnector connector;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean test(Api api) {
        return Api.Cdiscount == api;
    }

//    /**
//     * Searches a product.
//     *
//     * @param search The search keywords.
//     * @return A list of products that match the search.
//     */
//    public List<RemoteProduct> searchOld(String search) {
//        Objects.requireNonNull(search);
//
//        ParameterBag bag = ParameterBag.createSingle("Keyword", search);
//        List<JSONObject> results = connector.fetch(QueryType.Search, bag);
//
//        List<RemoteProduct> products = new LinkedList<>();
//        results.forEach(result -> products.add(new JsonObjectToProduct().apply(result)));
//
//        return products;
//    }

    public List<RemoteProduct> search(SearchBuilder builder) {
        Objects.requireNonNull(builder);

        SearchBuilder.Search search = builder.build();



        return null;
    }

    /**
     *
     * @param search
     * @param pageable
     * @return
     */
    public Page<RemoteProduct> search(String search, Pageable pageable) {
        Objects.requireNonNull(search);
        Objects.requireNonNull(pageable);

        return null;
    }

}
