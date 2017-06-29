package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.service.dto.ProductDto;
import com.gvstave.mistergift.service.misc.StringUtils;
import com.gvstave.sdk.cdiscount.domain.RemoteProduct;

import java.util.Date;


/**
 * Mapper for product.
 */
public class ProductMapper
{

    /**
     * Maps from product dto into regular product one.
     *
     * @return The product.
     */
    public static Product unmap(ProductDto dto) {
       Product product = new Product();
       product.setName(dto.getName());
       product.setDescription(dto.getDescription());
       product.setBrand(dto.getBrand());
       product.setDate(dto.getDate());
       product.setId(dto.getId());
       product.setPictureId(dto.getPictureId());
       product.setReference(dto.getReference());
       product.setUrl(dto.getUrl());
       product.setSlug(dto.getSlug());
       return product;
    }

    /**
     * Maps from remote product into regular one.
     *
     * @param remoteProduct The remote product.
     * @return The product.
     */
    public static Product unmap(RemoteProduct remoteProduct) {
        Product product = new Product();
        product.setReference(remoteProduct.getApiId());
        product.setSlug(StringUtils.toSlug(remoteProduct.getName()));
        product.setUrl(remoteProduct.getUrl());
        product.setPictureUrl(remoteProduct.getPictureUrl());
        product.setDate(new Date());
        product.setBrand(remoteProduct.getBrand());
        product.setDescription(remoteProduct.getDescription());
        product.setName(remoteProduct.getName());
        product.setProvider(remoteProduct.getProvider());
        return product;
    }

    /**
     * Maps from regular product into its dto.
     *
     * @param product The regular product.
     * @return The dto product.
     */
    public static ProductDto map(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setDescription(product.getDescription());
        dto.setDate(product.getDate());
        dto.setUrl(product.getUrl());
        dto.setId(product.getId());
        dto.setPictureId(product.getPictureId());
        dto.setReference(product.getReference());
        dto.setSlug(product.getSlug());
        return dto;
    }

}
