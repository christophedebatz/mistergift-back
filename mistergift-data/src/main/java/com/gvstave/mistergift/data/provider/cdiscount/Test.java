package com.gvstave.mistergift.data.provider.cdiscount;

import com.gvstave.sdk.cdiscount.api.CdiscountConnector;
import com.gvstave.sdk.cdiscount.api.CdiscountProductSearchQuery;
import com.gvstave.sdk.cdiscount.api.Connector;
import com.gvstave.sdk.cdiscount.api.product.response.CdiscountSearchProductResponse;
import com.gvstave.sdk.cdiscount.api.product.search.CdiscountProductSearchRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class Test {

    @Inject
    private CdiscountConnector connector;

    /**
     *
     * @return
     */
    public void getProducts(String search) {
        CdiscountProductSearchQuery searchQuery = new CdiscountProductSearchQuery();
        CdiscountProductSearchRequest searchRequest = new CdiscountProductSearchRequest(search);
        searchQuery.setSearchRequest(searchRequest);
        CdiscountSearchProductResponse response = (CdiscountSearchProductResponse) connector.fetch(Connector.CdiscountQueryType.Search, searchQuery);
    }
}
