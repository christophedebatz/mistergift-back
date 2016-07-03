package com.gvstave.mistergift.api.controller;

import com.gvstave.mistergift.api.access.exception.TooManyRequestException;
import com.gvstave.mistergift.config.annotation.UserRestricted;
import com.gvstave.mistergift.provider.api.ProductSupplierService;
import com.gvstave.mistergift.provider.api.SearchBuilder;
import com.gvstave.mistergift.provider.common.ProductComparator;
import com.gvstave.mistergift.provider.domain.RemoteProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController extends AbstractController {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    /** The product suppliers. */
    @Inject
    private List<ProductSupplierService> productSuppliers;

    /** The environment. */
    @Inject
    private Environment environment;

    /**
     * Default constructor.
     */
    public SearchController() throws TooManyRequestException {
        super();
    }

    /**
     * Returns the list of the users.
     *
     * @return Serialized users list.
     */
    @UserRestricted
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody List<RemoteProduct> searchProduct(HttpServletRequest request, @RequestParam(value = "query", required = true) String query) {
        LOGGER.debug("Searching remoteProducts with query={}", query);
        SearchBuilder builder = SearchBuilder.create()
                .withQuery(query);

        // current page requested
        if (!request.getParameter("page").isEmpty()) {
            builder.withPagination(
                Integer.parseInt(request.getParameter("page")),
                Integer.parseInt(environment.getProperty("page.size"))
            );
        }

        // include market places
        if (!request.getParameter("market").isEmpty()) {
            builder.includeMarketPlace(Boolean.parseBoolean(request.getParameter("market")));
        }

        // price between
        if (!request.getParameter("minPrice").isEmpty() && !request.getParameter("maxPrice").isEmpty()) {
            builder.withPriceBetween(
                Integer.parseInt(request.getParameter("minPrice")),
                Integer.parseInt(request.getParameter("maxPrice"))
            );
        }

        // brands
        if (!request.getParameter("brands").isEmpty()) {
            List<String> brands = Arrays.asList(
                request.getParameter("brands")
                    .replaceAll("\\s", "")
                    .split(",")
            );

            builder.withBrands(brands);
        }

        // get remoteProducts list
        List<RemoteProduct> remoteProducts = new ArrayList<>();
        for (ProductSupplierService productSupplier : productSuppliers) {
            remoteProducts.addAll(productSupplier.search(builder));
        }

        // sort: price asc, desc; name asc, desc
        String sort = request.getParameter("sort");
        if (!sort.isEmpty()) {
            String[] sorts = sort.replaceAll("\\s", "").split(":");
            if (sorts.length == 2) {
                remoteProducts.sort(
                    new ProductComparator(
                        ProductComparator.SortItem.of(sorts[0]),
                        ProductComparator.SortDirection.of(sorts[1])
                    )
                );
            }
        }

        return remoteProducts;
    }

}
