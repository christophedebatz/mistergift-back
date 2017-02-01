package com.gvstave.mistergift.data.service.query;

import com.gvstave.mistergift.data.domain.es.Product;
import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.domain.jpa.User;
import com.gvstave.mistergift.data.persistence.es.service.ProductPersistenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Objects;

/**
 * The {@link Product} service.
 */
@Service
public class ProductService {

    /** The gift persistence service. */
    @Inject
    private ProductPersistenceService productPersistenceService;

    /**
     * Returns a pageable response of user gift.
     *
     * @param user The user.
     * @param pageable The page request.
     * @return A page of {@link Gift}.
     */
    public Page<Gift> getUserWishList(User user, Pageable pageable) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(pageable);

//        return productPersistenceService.findAll(QPro, pageable);
        return null;
    }

}
