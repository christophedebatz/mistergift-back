package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.controller.annotation.UserRestricted;
import com.gvstave.mistergift.api.response.PageResponse;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.domain.jpa.Gift;
import com.gvstave.mistergift.data.exception.TooManyRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is the controller for gift requests.
 */
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

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
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public @ResponseBody Product getProductById(@RequestParam("id") Long id) {
        // return productPersistenceService.findOne(id);
        return null;
    }

    /**
     *
     * @param query
     * @return
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/search")
    public @ResponseBody PageResponse<Product> search(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "q") String query) {
        Objects.requireNonNull(query);

        LOGGER.debug("Searching products with query={} and page={}", query, page);
        PageRequest pageRequest = getPageRequest(page);

//        Predicate predicate = QProduct.product.name.containsIgnoreCase(query)
//                .or(QProduct.product.description.containsIgnoreCase(query));

        return new PageResponse<>(new PageImpl<>(new ArrayList<>()));
    }

    /**
     *
     * @param page
     * @param since
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, path = "/last")
    public @ResponseBody PageResponse<Product> getLastProducts(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @PathVariable(value = "since") Long since) {
        Objects.requireNonNull(since);

        PageRequest pageRequest = getPageRequest(page);
        LOGGER.debug("Retrieving last created products, since={} and page={}", since, pageRequest);

//        Predicate predicate = QProduct.product.creationDate.after(new Date(since));
        return new PageResponse<>(new PageImpl<>(new ArrayList<>()));
    }

}
