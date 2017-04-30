package com.gvstave.mistergift.data.service.dto.mapper;

import com.gvstave.mistergift.data.domain.jpa.GiftComment;
import com.gvstave.mistergift.data.service.dto.CommentDto;
import com.gvstave.sdk.cdiscount.api.product.response.CdiscountOfferResponse;
import com.gvstave.sdk.cdiscount.api.product.response.CdiscountProductResponse;
import com.gvstave.sdk.cdiscount.api.product.response.CdiscountSearchProductResponse;
import com.gvstave.sdk.cdiscount.domain.Api;
import com.gvstave.sdk.cdiscount.domain.RemoteProduct;
import com.gvstave.sdk.cdiscount.service.Provider;

import java.rmi.Remote;
import java.util.Optional;

/**
 *
 */
public class RemoteProductMapper
{
    /**
     *
     * @param product
     * @return
     */
    public static RemoteProduct mapCdiscount(CdiscountProductResponse product) {
        RemoteProduct remote = new RemoteProduct();
        remote.setProvider(Provider.CDISCOUNT);
        remote.setBrand(product.getBrand());
        remote.setDescription(product.getDescription());
        remote.setName(product.getName());
        remote.setApiId(product.getId());
        remote.setPictureUrl(product.getPictureUrl());
        remote.setRating(product.getRating());

        if (product.getBestOffer() != null && product.getBestOffer().getAvailable()) {
            remote.setPrice(product.getBestOffer().getPrice());
            remote.setUrl(product.getBestOffer().getProductUrl());

            if (product.getBestOffer().getSeller() != null) {
                remote.setSeller(product.getBestOffer().getSeller().getName());
            }

            CdiscountOfferResponse.CdiscountPriceDetailsResponse priceDetails = product.getBestOffer().getPriceDetails();
            if (priceDetails != null && priceDetails.getReferencePrice() != null) {
                remote.setDiscount(priceDetails.getReferencePrice() - product.getBestOffer().getPrice());
            }
        }

        return remote;
    }


}
