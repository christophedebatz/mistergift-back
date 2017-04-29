package com.gvstave.mistergift.data.service.command;

import com.gvstave.mistergift.data.domain.jpa.*;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.exception.InvalidFieldValueException;
import com.gvstave.mistergift.data.exception.UnauthorizedOperationException;
import com.gvstave.mistergift.data.service.dto.ProductDto;
import com.gvstave.mistergift.data.service.dto.mapper.ProductMapper;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

/**
 * Service that write event invitations.
 */
@Service
public class ProductWriterService {

    /** The mongo template. */
    @Inject
    private MongoOperations mongo;

    /**
     * Creates a new product manually.
     *
     * @param productDto The product.
     * @return The new product.
     */
    public void createNewProduct(ProductDto productDto) {
        mongo.save(ProductMapper.unmap(productDto));
    }

}
