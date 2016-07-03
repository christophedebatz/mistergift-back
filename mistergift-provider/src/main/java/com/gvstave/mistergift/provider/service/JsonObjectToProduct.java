package com.gvstave.mistergift.provider.service;

import com.gvstave.mistergift.provider.domain.RemoteProduct;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.function.Function;

/**
 * Manages the transformation from a json object into a product.
 */
public class JsonObjectToProduct implements Function<JSONObject, RemoteProduct> {

    /**
     * {@inheritDoc}
     */
    @Override
    public RemoteProduct apply(JSONObject object) {
        RemoteProduct remoteProduct = new RemoteProduct();

        try {
            if (object.has("name")) {
                remoteProduct.setName(object.get("value").toString());
            }
            if (object.has("description")) {
                remoteProduct.setDescription(object.get("description").toString());
            }
            if (object.has("price")) {
                remoteProduct.setPrice(Double.valueOf(object.get("price").toString()));
            }
            if (object.has("pictureUrl")) {
                remoteProduct.setPictureUrl(object.get("pictureUrl").toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return remoteProduct;
    }
}
