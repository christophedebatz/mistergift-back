package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import com.gvstave.mistergift.data.service.dto.SearchRequestDto;
import com.gvstave.mistergift.data.service.query.ProductService;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

/**
 * This is the controller for gift requests.
 */
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    /** The product service. */
    @Inject
    private ProductService productService;

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
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Product getProductById(@RequestParam("id") String id) {
        return productService.findById(id);
    }

    /**
     * Search for a product.
     *
     * @param page The page.
     * @param limit The limit.
     * @param query The search.
     * @return The products.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<Product> search(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
            @PathVariable(value = "q") String query) {
        LOGGER.debug("Searching products with query={}, page={} and limit={}", query, page, limit);
        return new PageResponse<>(productService.search(query, getPageRequest(page, limit)));
    }

    /**
     * Allow an advanced search for product.
     *
     * @param page The page.
     * @param limit The limit.
     * @param search The advanced search dto.
     * @return The products.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public PageResponse<Product> searchWith(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
            @RequestBody SearchRequestDto search){
        LOGGER.debug("Searching products with query={}, page={} and limit={}", search, page, limit);
        return new PageResponse<>(productService.search(search, getPageRequest(page, limit)));
    }

    /**
     * Returns the last products.
     *
     * @param limit The limit.
     * @param since The since date.
     * @return The last products.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/last")
    public List<Product> getLastProducts(
            @RequestParam(value = "limit", required = false, defaultValue = "1") Integer limit,
            @RequestParam(value = "since", required = false) Date since) {
        return productService.getLastProducts(limit, since);
    }

}
