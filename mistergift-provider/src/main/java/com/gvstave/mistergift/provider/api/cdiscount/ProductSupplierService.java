package com.gvstave.mistergift.provider.api.cdiscount;

import com.gvstave.mistergift.provider.domain.Product;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class ProductSupplierService {

    /** The environment. */
    @Inject
    private Environment environment;

    /**
     *
     * @param search
     * @return
     */
    public List<Product> search(String search) {
        Objects.requireNonNull(search);

        String apiKey = environment.getProperty("cdiscount.api.key");

        return null;
    }

}
