package com.gvstave.mistergift.provider.api.cdiscount;

import com.gvstave.mistergift.provider.api.QueryType;
import com.gvstave.mistergift.provider.api.cdiscount.connector.CdiscountConnector;
import com.gvstave.mistergift.provider.common.ParameterBag;
import com.gvstave.mistergift.provider.domain.Product;
import com.gvstave.mistergift.provider.service.JsonObjectToProduct;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
public class ProductSupplierService {

    /** The environment. */
    @Inject
    private Environment environment;

    /** The CDiscount connector. */
    @Inject
    private CdiscountConnector connector;

    /**
     *
     * @param search
     * @return
     */
    public List<Product> search(String search) {
        Objects.requireNonNull(search);

        ParameterBag bag = ParameterBag.createSingle("Keyword", search);

        List<JSONObject> results = connector.fetch(QueryType.Search, bag);
        List<Product> products = new LinkedList<>();
        results.forEach(result -> products.add(new JsonObjectToProduct().apply(result)));

        return null;
    }

}
