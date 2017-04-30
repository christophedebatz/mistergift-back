package com.gvstave.mistergift.data.provider.cdiscount;

import com.gvstave.mistergift.data.service.dto.mapper.RemoteProductMapper;
import com.gvstave.sdk.cdiscount.api.CdiscountConnector;
import com.gvstave.sdk.cdiscount.api.CdiscountProductSearchQuery;
import com.gvstave.sdk.cdiscount.api.Connector;
import com.gvstave.sdk.cdiscount.api.product.response.CdiscountProductResponse;
import com.gvstave.sdk.cdiscount.api.product.response.CdiscountSearchProductResponse;
import com.gvstave.sdk.cdiscount.api.product.search.CdiscountProductSearchRequest;
import com.gvstave.sdk.cdiscount.domain.RemoteProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSupplier {

    private static final int PRODUCTS_PER_PAGE = 5;

    @Inject
    private CdiscountConnector<CdiscountSearchProductResponse> connector;

    /**
     *
     * @return
     */
    public List<RemoteProduct> getProducts(String search, List<String> names, Pageable pageable) {
        CdiscountProductSearchQuery searchQuery = new CdiscountProductSearchQuery();
        CdiscountProductSearchRequest searchRequest = new CdiscountProductSearchRequest(search);
        searchQuery.setSearchRequest(searchRequest);
        CdiscountSearchProductResponse fetch = connector.fetch(Connector.CdiscountQueryType.Search, searchQuery);

        //@formatter:off
        if (fetch.getError() == null && fetch.getItemCount() != null && fetch.getItemCount() > 0) {
            return fetch.getProducts().stream()
                    .map(RemoteProductMapper::mapCdiscount)
                    .filter(product -> !names.contains(product.getName()))
                    .skip(pageable.getOffset())
                    .limit(PRODUCTS_PER_PAGE)
                    .collect(Collectors.toList());
        }
        //@formatter:on

        return null;
    }
}
