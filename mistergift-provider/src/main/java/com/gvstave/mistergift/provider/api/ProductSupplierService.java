package com.gvstave.mistergift.provider.api;

import com.gvstave.mistergift.provider.domain.Api;
import com.gvstave.mistergift.provider.domain.RemoteProduct;

import java.util.List;
import java.util.function.Predicate;

public interface ProductSupplierService extends Predicate<Api> {

    List<RemoteProduct> search(SearchBuilder builder);
}
