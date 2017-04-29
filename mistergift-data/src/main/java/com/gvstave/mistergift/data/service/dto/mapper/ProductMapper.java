package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.Event;
import com.gvstave.mistergift.data.domain.mongo.Product;
import com.gvstave.mistergift.data.service.dto.EventDto;
import com.gvstave.mistergift.data.service.dto.ProductDto;


public class ProductMapper
{
    /**
     *
     * @return
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


}
