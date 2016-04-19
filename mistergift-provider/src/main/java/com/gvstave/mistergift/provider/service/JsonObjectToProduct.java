package com.gvstave.mistergift.provider.service;

import com.gvstave.mistergift.provider.domain.Product;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.function.Function;

/**
 *
 */
public class JsonObjectToProduct implements Function<JSONObject, Product> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Product apply(JSONObject object) {
        Product product = new Product();

        try {
            if (object.has("name")) {
                product.setName(object.get("value").toString());
            }
            if (object.has("description")) {
                product.setDescription(object.get("description").toString());
            }
            if (object.has("price")) {
                product.setPrice(Double.valueOf(object.get("price").toString()));
            }
            if (object.has("pictureUrl")) {
                product.setPictureUrl(object.get("pictureUrl").toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return product;
    }
}
