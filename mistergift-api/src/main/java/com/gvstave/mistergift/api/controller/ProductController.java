package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.data.domain.Gift;
import com.gvstave.mistergift.data.domain.Product;
import com.gvstave.mistergift.data.persistence.ProductPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * This is the controller for gift requests.
 */
@UserRestricted
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    /** The gift persistence service. */
    @Inject
    private ProductPersistenceService productPersistenceService;

    /**
     * Default constructor.
     */
    public ProductController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns a {@link Gift}.
     *
     * @return Serialized gift.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody Product getProductById(@RequestParam("id") Long id) {
        return productPersistenceService.findOne(id);
    }

}
